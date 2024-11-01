package com.pitercapistrano.listadetarefacompose.repositorio

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.pitercapistrano.listadetarefacompose.datasource.Auth
import com.pitercapistrano.listadetarefacompose.listener.ListenerAuth
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AuthRepositorio @Inject constructor(private val auth: Auth, private val firebaseAuth: FirebaseAuth) {

    fun cadastro(nome: String, email: String, senha: String, confirmarSenha: String, listenerAuth: ListenerAuth) {
        auth.cadastro(nome, email, senha, confirmarSenha, listenerAuth)
    }

    fun signInWithGoogle(idToken: String, listenerAuth: ListenerAuth) {
        auth.signInWithGoogle(idToken, listenerAuth)
    }

    fun getCurrentUser() = firebaseAuth.currentUser
}