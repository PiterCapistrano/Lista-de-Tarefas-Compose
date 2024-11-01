package com.pitercapistrano.listadetarefacompose.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.pitercapistrano.listadetarefacompose.listener.ListenerAuth
import com.pitercapistrano.listadetarefacompose.repositorio.AuthRepositorio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepositorio: AuthRepositorio,
): ViewModel() {

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> get() = _user

    fun cadastro(nome: String, email: String, senha: String, confirmarSenha: String, listenerAuth: ListenerAuth) {
        authRepositorio.cadastro(nome, email, senha, confirmarSenha, listenerAuth)
        // Atualize o usuário logado
        _user.value = authRepositorio.getCurrentUser()  // Atribuindo o FirebaseUser completo
    }

    fun signInWithGoogle(idToken: String, listenerAuth: ListenerAuth) {
        authRepositorio.signInWithGoogle(idToken, listenerAuth)
    }

    fun getCurrentUser() = authRepositorio.getCurrentUser()
}