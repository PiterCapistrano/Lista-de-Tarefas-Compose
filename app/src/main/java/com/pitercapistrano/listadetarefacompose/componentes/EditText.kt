package com.pitercapistrano.listadetarefacompose.componentes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.pitercapistrano.listadetarefacompose.ui.theme.LightBlue

@Composable
fun EditText(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier,
    maxLines: Int
) {
    OutlinedTextField(
        value = value ,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        maxLines = maxLines,
        colors = OutlinedTextFieldDefaults.colors(
            focusedLabelColor = LightBlue,
            focusedBorderColor = LightBlue,
            cursorColor = LightBlue,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White
        )
    )
}