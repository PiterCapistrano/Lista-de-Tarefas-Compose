package com.pitercapistrano.listadetarefacompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.pitercapistrano.listadetarefacompose.listener.ListenerAuth
import com.pitercapistrano.listadetarefacompose.ui.theme.ListaDeTarefaComposeTheme
import com.pitercapistrano.listadetarefacompose.view.ListaTarefas
import com.pitercapistrano.listadetarefacompose.view.Login
import com.pitercapistrano.listadetarefacompose.view.SalvarTarefas
import com.pitercapistrano.listadetarefacompose.view.TelaCadastro
import com.pitercapistrano.listadetarefacompose.viewModel.AuthViewModel
import com.pitercapistrano.listadetarefacompose.viewModel.TarefasViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListaDeTarefaComposeTheme {
                
                val navController = rememberNavController()
                val tarefasViewModel: TarefasViewModel = hiltViewModel()
                val authViewModel: AuthViewModel = hiltViewModel()

                authViewModel.user.observe(this) { user ->
                    if (user != null) {
                        navController.navigate("listaTarefas") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
                
                NavHost(navController = navController, startDestination =  "login"){

                    composable(
                        route = "login"
                    ){
                        Login(navController, authViewModel, onSignInSuccess = {user ->
                            navController.navigate("listaTarefa")
                        })
                    }

                    composable(
                        route = "telaCadastro"
                    ){
                        TelaCadastro(navController, authViewModel)
                    }

                    composable(
                        route = "listaTarefas"
                    ){
                        ListaTarefas(navController, tarefasViewModel)
                    }

                    composable(
                        route = "salvarTarefas"
                    ){
                        SalvarTarefas(navController, tarefasViewModel)
                    }
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val idToken = account.idToken // Pegue o idToken do GoogleSignInAccount
                viewModel.signInWithGoogle(idToken!!, object : ListenerAuth {
                    override fun onSucess(message: String, route: String) {
                        // Navegar para a tela de tarefas
                    }

                    override fun onFailure(message: String) {
                        // Mostrar erro
                    }

                    override fun erroSenha(message: String) {
                        // Lidar com erro de senha
                    }
                })
            } catch (e: ApiException) {
                // Gerenciar erros
            }
        }
    }
    companion object {
        const val RC_SIGN_IN = 9001
    }
}