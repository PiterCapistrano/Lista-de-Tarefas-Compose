package com.pitercapistrano.listadetarefacompose.view

import androidx.compose.foundation.ScrollState
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pitercapistrano.listadetarefacompose.componentes.Botao
import com.pitercapistrano.listadetarefacompose.componentes.EditText
import com.pitercapistrano.listadetarefacompose.ui.theme.Blue
import com.pitercapistrano.listadetarefacompose.ui.theme.DarkGreen
import com.pitercapistrano.listadetarefacompose.ui.theme.DarkRed
import com.pitercapistrano.listadetarefacompose.ui.theme.DarkYellow
import com.pitercapistrano.listadetarefacompose.ui.theme.Green
import com.pitercapistrano.listadetarefacompose.ui.theme.Red
import com.pitercapistrano.listadetarefacompose.ui.theme.Yellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalvarTarefas(
    navController: NavController
){
    var inputTitulo by remember {
        mutableStateOf("")
    }

    var inputDescricao by remember {
        mutableStateOf("")
    }

    var semPrioridade by remember {
        mutableStateOf(false)
    }

    var prioridadeBaixa by remember {
        mutableStateOf(false)
    }

    var prioridadeMedia by remember {
        mutableStateOf(false)
    }

    var prioridadeAlta by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Salvar Tarefa", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Blue,
                    titleContentColor = Color.White
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
                    selected = prioridadeBaixa,
                    onClick = { prioridadeBaixa = !prioridadeBaixa },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Green,
                        unselectedColor = DarkGreen
                    )
                )

                RadioButton(
                    selected = prioridadeMedia,
                    onClick = { prioridadeMedia = !prioridadeMedia},
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Yellow,
                        unselectedColor = DarkYellow
                    )
                )

                RadioButton(
                    selected = prioridadeAlta,
                    onClick = { prioridadeAlta = !prioridadeAlta},
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Red,
                        unselectedColor = DarkRed
                    )
                )

            }

            Botao(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                text = "Salvar Tarefa"
            )
        }
    }
}

@Preview
@Composable
fun SalvarTarefasPreview(){
    SalvarTarefas(navController = rememberNavController())
}