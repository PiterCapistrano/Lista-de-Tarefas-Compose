package com.pitercapistrano.listadetarefacompose.itemLista

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.pitercapistrano.listadetarefacompose.R
import com.pitercapistrano.listadetarefacompose.model.Tarefa
import com.pitercapistrano.listadetarefacompose.ui.theme.DarkGreen
import com.pitercapistrano.listadetarefacompose.ui.theme.DarkRed
import com.pitercapistrano.listadetarefacompose.ui.theme.DarkYellow
import com.pitercapistrano.listadetarefacompose.ui.theme.Green
import com.pitercapistrano.listadetarefacompose.ui.theme.Red

@Composable
fun TarefaItem(
    position: Int,
    listaTarefas: MutableList<Tarefa>
){

    val tituloTarefa = listaTarefas[position].titulo
    val descricaoTarefa = listaTarefas[position].descricao
    val prioridadeTarefa = listaTarefas[position].prioridade

    var nivelPrioridade: String = when(prioridadeTarefa){
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

            val (txtTitulo, txtDescricao, cardPrioridade, txtPrioridade, btDeletar) = createRefs()

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
                onClick = { /*TODO*/ },
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
        }
    }
}