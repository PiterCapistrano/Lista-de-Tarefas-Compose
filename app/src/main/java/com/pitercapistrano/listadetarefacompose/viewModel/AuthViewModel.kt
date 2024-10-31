package com.pitercapistrano.listadetarefacompose.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pitercapistrano.listadetarefacompose.listener.ListenerAuth
import com.pitercapistrano.listadetarefacompose.repositorio.AuthRepositorio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepositorio: AuthRepositorio): ViewModel() {

    fun cadastro(nome: String, email: String, senha: String, confirmarSenha:String, listenerAuth: ListenerAuth){
        viewModelScope.launch {
            authRepositorio.cadastro(nome, email, senha, confirmarSenha, listenerAuth)
        }
    }
}