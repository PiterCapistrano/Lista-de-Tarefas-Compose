package com.pitercapistrano.listadetarefacompose.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pitercapistrano.listadetarefacompose.R
import com.pitercapistrano.listadetarefacompose.itemLista.TarefaItem
import com.pitercapistrano.listadetarefacompose.ui.theme.Blue
import com.pitercapistrano.listadetarefacompose.viewModel.TarefasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaTarefas(
    navController: NavController,
    viewModel: TarefasViewModel = hiltViewModel()
){

    val context = LocalContext.current

    val listaTarefas = viewModel.recuperarTarefas().collectAsState(mutableListOf()).value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Lista de Tarefas", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Blue,
                    titleContentColor = Color.White
                )
            )
        },
        
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("salvarTarefas") },

                containerColor = Blue,
                contentColor = Color.White,
                shape = RoundedCornerShape(50.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_add),
                    contentDescription = "Botão de adicionar tarefa"
                )
            }
        },

        containerColor = Color.Black
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            itemsIndexed(listaTarefas){position, _ ->
                TarefaItem(position = position, listaTarefas = listaTarefas, context = context, navController = navController, viewModel)
            }

        }
    }
}

@Preview
@Composable
fun ListaTarefaPreview(){
    ListaTarefas(navController = rememberNavController())
}