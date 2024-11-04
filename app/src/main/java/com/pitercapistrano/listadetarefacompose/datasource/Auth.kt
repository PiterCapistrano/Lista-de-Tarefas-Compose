package com.pitercapistrano.listadetarefacompose.datasource

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.pitercapistrano.listadetarefacompose.listener.ListenerAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class Auth @Inject constructor() {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    val _verificarUsuarioLogado = MutableStateFlow<Boolean>(false)
    val verificarUsuarioLogado: StateFlow<Boolean> = _verificarUsuarioLogado

    fun cadastro(
        nome:String,
        email: String,
        senha: String,
        confirmarSenha: String,
        listenerAuth: ListenerAuth
    ){

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()){
            listenerAuth.onFailure("Preencha todos os campos!")
        }else if (senha != confirmarSenha){
            listenerAuth.erroSenha("Erro ao confirmar a senha!")
        }else{
            auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener {cadastro ->
                if (cadastro.isSuccessful){

                    val usuarioID = FirebaseAuth.getInstance().currentUser?.uid.toString()

                    val dadosUsuarioMap = hashMapOf(
                        "nome" to nome,
                        "email" to email,
                        "usuarioID" to usuarioID
                    )

                    db.collection("usuarios").document(usuarioID).set(dadosUsuarioMap).addOnCompleteListener {
                        listenerAuth.onSucess("Sucesso ao cadastrar o usuário!", "login")
                    }.addOnFailureListener {
                        listenerAuth.onFailure("Erro inesperado!")
                    }
                }
            }.addOnFailureListener {exception ->
                val erro = when(exception){
                    is FirebaseAuthUserCollisionException -> "Está conta já foi cadastrada!"
                    is FirebaseAuthWeakPasswordException -> "A senha deve ter no mínimo 6 caracteres!"
                    is FirebaseNetworkException -> "Sem conexão com a internet!"
                    else -> "E-mail inválido!"
                }
                listenerAuth.onFailure(erro)
            }
        }

    }

    // Novo método para login com Google
    fun signInWithGoogle(idToken: String, listenerAuth: ListenerAuth) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val usuarioID = auth.currentUser?.uid
                val nome = auth.currentUser?.displayName
                val email = auth.currentUser?.email

                if (usuarioID != null) {
                    db.collection("usuarios").document(usuarioID).get().addOnSuccessListener { document ->
                        if (!document.exists()) {
                            val dadosUsuarioMap = hashMapOf(
                                "nome" to nome,
                                "email" to email,
                                "usuarioID" to usuarioID
                            )

                            db.collection("usuarios").document(usuarioID).set(dadosUsuarioMap).addOnCompleteListener {
                                listenerAuth.onSucess("Sucesso ao cadastrar usuário via Google!", "listaTarefas") // Navega após o cadastro
                            }.addOnFailureListener {
                                listenerAuth.onFailure("Erro ao salvar dados do usuário.")
                            }
                        } else {
                            listenerAuth.onSucess("Login realizado com sucesso!", "listaTarefas") // Navega após o login
                        }
                    }.addOnFailureListener {
                        listenerAuth.onFailure("Erro ao acessar dados do usuário.")
                    }
                }
            } else {
                listenerAuth.onFailure("Erro ao autenticar com Google.")
            }
        }
    }

    fun login(email: String, senha: String, listenerAuth: ListenerAuth){
        if (email.isEmpty() || senha.isEmpty()){
            listenerAuth.onFailure("Preencha todos os campos!")
        }else{
            auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener {autenticacao ->
                if (autenticacao.isSuccessful){
                    listenerAuth.onSucess("Sucesso ao fazer o login!", "listaTarefas")
                }
            }.addOnFailureListener {exception ->
                val erro = when(exception){
                    is FirebaseAuthInvalidCredentialsException -> "E-mail ou Senha incorreta!"
                    is FirebaseNetworkException -> "Sem conexão com a internet!"
                    else -> "E-mail inválido!"
                }
                listenerAuth.onFailure(erro)
            }
        }
    }

    fun verificarLogin(): Flow<Boolean> {
        val usuarioLogado = FirebaseAuth.getInstance().currentUser

        _verificarUsuarioLogado.value = usuarioLogado != null
        return verificarUsuarioLogado
    }
}