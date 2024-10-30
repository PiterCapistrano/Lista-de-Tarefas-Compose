package com.pitercapistrano.listadetarefacompose.componentes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.pitercapistrano.listadetarefacompose.ui.theme.Blue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginEditText(
    onClick: () -> Unit,
    value: String,
    onValue: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType,
    painter: Painter,
    contentDescription: String,
    visualTransformation: VisualTransformation
){
    OutlinedTextField(
        value = value ,
        onValueChange = onValue,
        label = {
            Text(text = label)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp, 20.dp, 10.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            cursorColor = Blue,
            focusedBorderColor = Blue,
            focusedLabelColor = Blue,
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        trailingIcon = {
           IconButton(
               onClick = onClick
           ) {
               Icon(
                   painter = painter,
                   contentDescription = contentDescription
               )
           }
        },
        maxLines = 1,
        visualTransformation = visualTransformation
    )
}