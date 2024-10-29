package com.pitercapistrano.listadetarefacompose.itemLista

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pitercapistrano.listadetarefacompose.R
import com.pitercapistrano.listadetarefacompose.model.Tarefa
import com.pitercapistrano.listadetarefacompose.ui.theme.DarkGreen
import com.pitercapistrano.listadetarefacompose.ui.theme.DarkRed
import com.pitercapistrano.listadetarefacompose.ui.theme.DarkYellow
import com.pitercapistrano.listadetarefacompose.ui.theme.Green
import com.pitercapistrano.listadetarefacompose.ui.theme.Red
import com.pitercapistrano.listadetarefacompose.viewModel.TarefasViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun TarefaItem(
    position: Int,
    listaTarefas: MutableList<Tarefa>,
    context: Context,
    navController: NavController,
    viewModel: TarefasViewModel = hiltViewModel()
){

    val tituloTarefa = listaTarefas[position].tarefa
    val descricaoTarefa = listaTarefas[position].descricao
    val prioridadeTarefa = listaTarefas[position].prioridade
    val tarefaConcluida = listaTarefas[position].checkTarefa

    val scope = rememberCoroutineScope()

    var isChecked by remember {
        mutableStateOf(tarefaConcluida)
    }

    fun dialogDeletar(){
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Deletar Tarefa")
            .setMessage("Deseja excluir a tarefa?")
            .setPositiveButton("Sim"){_,_ ->
                viewModel.deletarTarefa(tituloTarefa.toString())

                scope.launch(Dispatchers.Main) {
                    listaTarefas.removeAt(position)
                    navController.navigate("listaTarefas")
                    Toast.makeText(context, "Sucesso ao deletar a tarefa!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Não"){_,_ ->

            }
            .show()
    }

    val nivelPrioridade: String = when(prioridadeTarefa){
        0 -> {"Sem Prioridade"}
        1 -> {"Baixa Prioridade"}
        2 -> {"Média Prioridade"}
        else -> {"Alta Prioridade"}
    }

    val color = when(prioridadeTarefa){
        0 -> {Color.Black}
        1 -> {DarkGreen}
        2 -> {DarkYellow}
        else -> {DarkRed}
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.padding(20.dp)
        ) {

            val (txtTitulo, txtDescricao, cardPrioridade, txtPrioridade, btDeletar, checkTarefa) = createRefs()

            Text(
                text = tituloTarefa.toString(),
                modifier = Modifier.constrainAs(txtTitulo){
                    top.linkTo(parent.top, 10.dp)
                    start.linkTo(parent.start, 10.dp)
                }
            )

            Text(
                text = descricaoTarefa.toString(),
                modifier = Modifier.constrainAs(txtDescricao){
                    top.linkTo(txtTitulo.bottom, 10.dp)
                    start.linkTo(parent.start, 10.dp)
                }
            )

            Text(
                text = nivelPrioridade,
                modifier = Modifier.constrainAs(txtPrioridade){
                    top.linkTo(txtDescricao.bottom, 20.dp)
                    start.linkTo(parent.start, 10.dp)
                    bottom.linkTo(parent.bottom,10.dp)
                }
            )

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = color
                ),
                modifier = Modifier
                    .size(20.dp)
                    .constrainAs(cardPrioridade) {
                        top.linkTo(txtDescricao.bottom, 20.dp)
                        start.linkTo(txtPrioridade.end, 10.dp)
                        bottom.linkTo(parent.bottom, 10.dp)
                    },
                shape = RoundedCornerShape(100.dp)
            ) {

            }
            IconButton(
                onClick = {
                          dialogDeletar()
                },
                modifier = Modifier.constrainAs(btDeletar){
                    top.linkTo(txtDescricao.bottom, 20.dp)
                    start.linkTo(cardPrioridade.end, 30.dp)
                    end.linkTo(parent.end, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Red,
                    containerColor = Color.White
                )
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete),
                    contentDescription = "Botão de deletar tarefa"
                )
            }

            Checkbox(
                checked = isChecked!!,
                onCheckedChange = {

                    isChecked = it

                    scope.launch(Dispatchers.IO){
                        if (isChecked!!){
                            isChecked = true
                            viewModel.atualizarTarefa(tituloTarefa!!, true)
                        }else{
                            viewModel.atualizarTarefa(tituloTarefa!!, false)
                        }
                    }
                    scope.launch(Dispatchers.Main){
                        if (isChecked!!){
                            Toast.makeText(context, "Tarefa marcada como concluída!", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(context, "Tarefa desmarcada!", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier.constrainAs(checkTarefa){
                    start.linkTo(btDeletar.end, 10.dp)
                    top.linkTo(txtDescricao.bottom, 20.dp)
                    end.linkTo(parent.end, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Green,
                    uncheckedColor = Color.Black
                )
            )
        }
    }
}