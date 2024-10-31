package com.pitercapistrano.listadetarefacompose.repositorio

import com.pitercapistrano.listadetarefacompose.datasource.Auth
import com.pitercapistrano.listadetarefacompose.listener.ListenerAuth
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AuthRepositorio @Inject constructor(private val auth: Auth) {

    fun cadastro(nome: String,email: String, senha: String, confirmarSenha:String, listenerAuth: ListenerAuth){
        auth.cadastro(nome, email, senha, confirmarSenha, listenerAuth)

    }
}