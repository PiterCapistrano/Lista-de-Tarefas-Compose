package com.pitercapistrano.listadetarefacompose.componentes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pitercapistrano.listadetarefacompose.ui.theme.LightBlue

@Composable
fun Botao(
    onClick: () -> Unit,
    modifier: Modifier,
    text: String
){
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = LightBlue,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(5.dp)
        ) {
        Text(text = text, fontWeight = FontWeight.Bold, fontSize = 18.sp)
    }
}