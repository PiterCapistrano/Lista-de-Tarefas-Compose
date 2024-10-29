package com.pitercapistrano.listadetarefacompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pitercapistrano.listadetarefacompose.ui.theme.ListaDeTarefaComposeTheme
import com.pitercapistrano.listadetarefacompose.view.ListaTarefas
import com.pitercapistrano.listadetarefacompose.view.Login
import com.pitercapistrano.listadetarefacompose.view.SalvarTarefas
import com.pitercapistrano.listadetarefacompose.view.TelaCadastro
import com.pitercapistrano.listadetarefacompose.viewModel.TarefasViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListaDeTarefaComposeTheme {
                
                val navController = rememberNavController()
                val tarefasViewModel: TarefasViewModel = hiltViewModel()
                
                NavHost(navController = navController, startDestination =  "login"){

                    composable(
                        route = "login"
                    ){
                        Login(navController, tarefasViewModel)
                    }

                    composable(
                        route = "telaCadastro"
                    ){
                        TelaCadastro(navController, tarefasViewModel)
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
}