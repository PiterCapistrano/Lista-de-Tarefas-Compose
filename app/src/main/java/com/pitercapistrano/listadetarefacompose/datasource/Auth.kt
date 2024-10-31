package com.pitercapistrano.listadetarefacompose.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.pitercapistrano.listadetarefacompose.listener.ListenerAuth
import javax.inject.Inject

class Auth @Inject constructor() {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
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
            }
        }

    }

}