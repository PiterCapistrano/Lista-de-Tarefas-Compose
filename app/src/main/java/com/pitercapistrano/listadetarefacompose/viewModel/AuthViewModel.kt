package com.pitercapistrano.listadetarefacompose.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.pitercapistrano.listadetarefacompose.listener.ListenerAuth
import com.pitercapistrano.listadetarefacompose.repositorio.AuthRepositorio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepositorio: AuthRepositorio,
): ViewModel() {

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> get() = _user

    fun cadastro(nome: String, email: String, senha: String, confirmarSenha: String, listenerAuth: ListenerAuth) {
        viewModelScope.launch {
            authRepositorio.cadastro(nome, email, senha, confirmarSenha, listenerAuth)
            // Atualize o usuário logado
            _user.value = getCurrentUser()  // Atribuindo o FirebaseUser completo
        }
    }

    fun signInWithGoogle(idToken: String, listenerAuth: ListenerAuth) {
        viewModelScope.launch {
            authRepositorio.signInWithGoogle(idToken, listenerAuth)
        }
    }

    private fun getCurrentUser() = authRepositorio.getCurrentUser()

    fun login(email: String, senha: String, listenerAuth: ListenerAuth){
        viewModelScope.launch {
            authRepositorio.login(email, senha, listenerAuth)
        }
    }

    fun verificarLogin(): Flow<Boolean>{
        return authRepositorio.verificarLogin()
    }
}