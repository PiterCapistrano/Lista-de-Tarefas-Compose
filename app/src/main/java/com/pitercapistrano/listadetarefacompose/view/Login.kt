package com.pitercapistrano.listadetarefacompose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pitercapistrano.listadetarefacompose.R
import com.pitercapistrano.listadetarefacompose.componentes.LoginEditText
import com.pitercapistrano.listadetarefacompose.ui.theme.DarkPBlue
import com.pitercapistrano.listadetarefacompose.ui.theme.DarkPink
import com.pitercapistrano.listadetarefacompose.viewModel.TarefasViewModel

@Composable
fun Login(
    navController: NavController,
    viewModel: TarefasViewModel = hiltViewModel()
){

    var email by remember {
        mutableStateOf("")
    }

    var senha by remember {
        mutableStateOf("")
    }
    
    var visibilidadeSenha by remember {
        mutableStateOf(false)
    }

    var icon = if (visibilidadeSenha){
        painterResource(id = R.drawable.baseline_visibility)
    }else{
        painterResource(id = R.drawable.baseline_visibility_off_24)
    }

    Scaffold(
        modifier = Modifier.background(
            brush = Brush.linearGradient(
                colors = listOf(
                    DarkPink,
                    DarkPBlue
                )
            )
        ),
        containerColor = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.baseline_task),
                contentDescription = "Icone de tarefas",
                tint = Color.White
            )

            LoginEditText(
                onClick = {},
                value = email,
                onValue = {email = it},
                label = "E-mail",
                keyboardType = KeyboardType.Email,
                painter = painterResource(id = R.drawable.baseline_email_24),
                contentDescription = "Icone de e-mail",
                visualTransformation = VisualTransformation.None
            )

            LoginEditText(
                onClick = {visibilidadeSenha = !visibilidadeSenha},
                value = senha,
                onValue = {senha = it},
                label = "Senha",
                keyboardType = KeyboardType.Password,
                painter = icon,
                contentDescription = "Icone de senha",
                visualTransformation = if (visibilidadeSenha) {
                    VisualTransformation.None
                }else{
                    PasswordVisualTransformation()
                }
            )
        }
    }
}