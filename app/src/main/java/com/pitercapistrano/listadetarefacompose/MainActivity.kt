package com.pitercapistrano.listadetarefacompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pitercapistrano.listadetarefacompose.ui.theme.ListaDeTarefaComposeTheme
import com.pitercapistrano.listadetarefacompose.view.ListaTarefas
import com.pitercapistrano.listadetarefacompose.view.SalvarTarefas

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListaDeTarefaComposeTheme {
                
                val navController = rememberNavController()
                
                NavHost(navController = navController, startDestination =  "listaTarefas"){
                    composable(
                        route = "listaTarefas"
                    ){
                        ListaTarefas(navController)
                    }

                    composable(
                        route = "salvarTarefas"
                    ){
                        SalvarTarefas(navController)
                    }
                }
            }
        }
    }
}