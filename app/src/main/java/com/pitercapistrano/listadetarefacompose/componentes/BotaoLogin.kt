package com.pitercapistrano.listadetarefacompose.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pitercapistrano.listadetarefacompose.ui.theme.DarkPBlue
import com.pitercapistrano.listadetarefacompose.ui.theme.DarkPink

@Composable
fun BotaoLogin(
    onClick: () -> Unit,
    text: String
){
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(87.dp)
            .padding(20.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        DarkPink,
                        DarkPBlue
                    )
                ),
                shape = RoundedCornerShape(50.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(50.dp),

        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            hoveredElevation = 0.dp,
            disabledElevation = 0.dp,
            focusedElevation = 0.dp
        ),
        border = BorderStroke(2.dp, Color.White)
    ) {
        Text(text = text, fontWeight = FontWeight.Medium, fontSize = 18.sp)
    }
}
