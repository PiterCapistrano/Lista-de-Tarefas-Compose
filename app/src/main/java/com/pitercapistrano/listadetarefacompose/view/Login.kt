package com.pitercapistrano.listadetarefacompose.view

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseUser
import com.pitercapistrano.listadetarefacompose.R
import com.pitercapistrano.listadetarefacompose.componentes.BotaoLogin
import com.pitercapistrano.listadetarefacompose.componentes.LoginEditText
import com.pitercapistrano.listadetarefacompose.listener.ListenerAuth
import com.pitercapistrano.listadetarefacompose.ui.theme.Blue
import com.pitercapistrano.listadetarefacompose.ui.theme.DarkPBlue
import com.pitercapistrano.listadetarefacompose.ui.theme.DarkPink
import com.pitercapistrano.listadetarefacompose.viewModel.AuthViewModel

@Composable
fun Login(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    onSignInSuccess: (FirebaseUser?) -> Unit
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

    var mensagem by remember {
        mutableStateOf("")
    }

    val icon = if (visibilidadeSenha){
        painterResource(id = R.drawable.baseline_visibility_off_24)
    }else{
        painterResource(id = R.drawable.baseline_visibility)
    }

    val user by authViewModel.user.observeAsState()

    LaunchedEffect(user) {
        user?.let { onSignInSuccess(it) } // Passa o usuário autenticado
    }

    val context = LocalContext.current

    // Use ActivityResultLauncher to handle the result
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                authViewModel.signInWithGoogle(account.idToken ?: "", object : ListenerAuth {
                    override fun onSucess(message: String, navigation: String) {
                        navController.navigate("listaTarefas") // Navegue após o login bem-sucedido
                    }

                    override fun onFailure(message: String) {
                        Log.e("Login", message)
                    }

                    override fun erroSenha(message: String) {
                        Log.e("Login", message)
                    }
                })
            } catch (e: ApiException) {
                Log.e("Error", "DeadObjectException occurred: ${e.message}")
                mensagem = "Erro ao obter informações do Google."
            }
        }
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
    ) {view ->
        Column(
            modifier = Modifier
                .padding(view)
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
            
            Text(text = mensagem, fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Color.White)

            BotaoLogin(
                onClick = {

                },
                text = "Entrar"
            )

            Button(
                modifier = Modifier
                    .padding(20.dp, 20.dp, 20.dp, 0.dp),
                onClick = {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken("755571819699-8j47aq8kbklatfdndguluhug58rcfcbb.apps.googleusercontent.com")
                        .requestEmail()
                        .build()
                    val googleSignInClient = GoogleSignIn.getClient(context, gso)
                    val signInIntent = googleSignInClient.signInIntent
                    launcher.launch(signInIntent)  // Launch the sign-in intent
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
                ) {
                Image(painter = painterResource(id = R.drawable.google72), contentDescription = "Simbulo da google" )
                Text(
                    text = "Entrar com uma conta Google",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Blue,
                    modifier = Modifier.padding(10.dp, 0.dp)
                    )
            }
            
            Spacer(modifier = Modifier.padding(20.dp))

            TextButton(
                onClick = { navController.navigate("telaCadastro") }
            ) {
                Text(
                    text = "Não tem conta? Cadastre-se agora!",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.padding(0.dp,0.dp,0.dp,20.dp))
        }
    }
}