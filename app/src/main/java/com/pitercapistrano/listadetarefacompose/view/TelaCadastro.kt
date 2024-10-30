package com.pitercapistrano.listadetarefacompose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pitercapistrano.listadetarefacompose.R
import com.pitercapistrano.listadetarefacompose.componentes.BotaoLogin
import com.pitercapistrano.listadetarefacompose.componentes.LoginEditText
import com.pitercapistrano.listadetarefacompose.ui.theme.cor1
import com.pitercapistrano.listadetarefacompose.ui.theme.cor2
import com.pitercapistrano.listadetarefacompose.ui.theme.cor3
import com.pitercapistrano.listadetarefacompose.ui.theme.cor4
import com.pitercapistrano.listadetarefacompose.viewModel.TarefasViewModel

@Composable
fun TelaCadastro(
    navController: NavController,
    viewModel: TarefasViewModel = hiltViewModel()
){

    var nome by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var senha by remember {
        mutableStateOf("")
    }

    var confirmarSenha by remember {
        mutableStateOf("")
    }

    var visibilidadeSenha by remember {
        mutableStateOf(false)
    }

    var mensagem by remember {
        mutableStateOf("")
    }

    val icon = if (visibilidadeSenha){
        painterResource(id = R.drawable.baseline_visibility_off_24)
    }else{
        painterResource(id = R.drawable.baseline_visibility)
    }

    Scaffold(
        modifier = Modifier.background(
            brush = Brush.linearGradient(
                colors = listOf(
                   cor1,
                   cor2,
                   cor3,
                   cor4
                )
            )
        ),
        containerColor = Color.Transparent
    ) {view ->
        Column(
            modifier = Modifier
                .padding(view)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            
            Text(
                text = "Tela de Cadastro",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(20.dp)
            )

            LoginEditText(
                onClick = {},
                value = nome,
                onValue = {nome = it},
                label = "Nome",
                keyboardType = KeyboardType.Text,
                painter = painterResource(id = R.drawable.baseline_person),
                contentDescription = "Icone de e-mail",
                visualTransformation = VisualTransformation.None
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

            LoginEditText(
                onClick = {},
                value = confirmarSenha,
                onValue = {confirmarSenha = it},
                label = "Confirmar Senha",
                keyboardType = KeyboardType.Password,
                painter = icon,
                contentDescription = "Icone de senha",
                visualTransformation = if (visibilidadeSenha) {
                    VisualTransformation.None
                }else{
                    PasswordVisualTransformation()
                }
            )
            
            Text(text = mensagem, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)

            BotaoLogin(
                onClick = {

                },
                text = "Cadastrar"
            )
        }
    }
}