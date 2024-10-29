package com.pitercapistrano.listadetarefacompose.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pitercapistrano.listadetarefacompose.componentes.Botao
import com.pitercapistrano.listadetarefacompose.componentes.EditText
import com.pitercapistrano.listadetarefacompose.constantes.Constantes
import com.pitercapistrano.listadetarefacompose.enumclass.Prio
import com.pitercapistrano.listadetarefacompose.ui.theme.Blue
import com.pitercapistrano.listadetarefacompose.ui.theme.DarkGreen
import com.pitercapistrano.listadetarefacompose.ui.theme.DarkRed
import com.pitercapistrano.listadetarefacompose.ui.theme.DarkYellow
import com.pitercapistrano.listadetarefacompose.ui.theme.Green
import com.pitercapistrano.listadetarefacompose.ui.theme.Red
import com.pitercapistrano.listadetarefacompose.ui.theme.Yellow
import com.pitercapistrano.listadetarefacompose.viewModel.TarefasViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalvarTarefas(
    navController: NavController,
    viewModel: TarefasViewModel = hiltViewModel()
){
    var inputTitulo by remember {
        mutableStateOf("")
    }

    var inputDescricao by remember {
        mutableStateOf("")
    }

    var prioridadeSelecionada by remember { mutableStateOf(Prio.NENHUMA) }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Salvar Tarefa", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Blue,
                    titleContentColor = White
                )
            )
        },

    ) { it ->
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            EditText(
                value = inputTitulo,
                onValueChange = {inputTitulo = it},
                label = "Titulo da Tarefa" ,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 20.dp, 20.dp, 10.dp),
                maxLines = 1
            )

            EditText(
                value = inputDescricao,
                onValueChange = {inputDescricao = it},
                label = "Descrição",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(20.dp, 0.dp, 20.dp, 10.dp),
                maxLines = 5
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                
                Text(text = "Nível de Prioridade:")

                RadioButton(
                    selected = prioridadeSelecionada == Prio.BAIXA,
                    onClick = { prioridadeSelecionada = Prio.BAIXA },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Green,
                        unselectedColor = DarkGreen
                    )
                )

                RadioButton(
                    selected = prioridadeSelecionada == Prio.MEDIA,
                    onClick = { prioridadeSelecionada = Prio.MEDIA },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Yellow,
                        unselectedColor = DarkYellow
                    )
                )

                RadioButton(
                    selected = prioridadeSelecionada == Prio.ALTA,
                    onClick = { prioridadeSelecionada = Prio.ALTA },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Red,
                        unselectedColor = DarkRed
                    )
                )

            }

            Botao(
                onClick = {

                    var mensagem = true

                   scope.launch(Dispatchers.IO) {
                       if (inputTitulo.isEmpty()) {
                           mensagem = false
                       } else {
                           val prioridade = when (prioridadeSelecionada) {
                               Prio.BAIXA -> Constantes.PRIORIDADE_BAIXA
                               Prio.MEDIA -> Constantes.PRIORIDADE_MEDIA
                               Prio.ALTA -> Constantes.PRIORIDADE_ALTA
                               else -> Constantes.SEM_PRIORIDADE
                           }
                           viewModel.salvarTarefa(inputTitulo, inputDescricao, prioridade, false)
                       }
                   }
                    scope.launch(Dispatchers.Main) {
                        if (mensagem){
                            Toast.makeText(context, "Sucesso ao salvar a tarefa!", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }else{
                            // Mostra a Snackbar
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Título da tarefa é obrigatório!",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                text = "Salvar Tarefa"
            )
            // Host para exibir a Snackbar
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.fillMaxWidth()
            ) { snackbarData ->
                Snackbar(
                    snackbarData = snackbarData,
                    containerColor = Red,
                    contentColor = White,
                )
            }
        }
    }
}

@Preview
@Composable
fun SalvarTarefasPreview(){
    SalvarTarefas(navController = rememberNavController())
}