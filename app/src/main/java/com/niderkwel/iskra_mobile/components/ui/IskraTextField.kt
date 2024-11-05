package com.niderkwel.iskra_mobile.components.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun IskraTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxLines: Int = 1,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        label = null,
        onValueChange = onValueChange,
        shape = MaterialTheme.shapes.medium,
        placeholder = { Text(label) },
        keyboardOptions = keyboardOptions,
        modifier = modifier.fillMaxWidth(),
        visualTransformation = visualTransformation,
        maxLines = maxLines,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
            focusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
        )
    )
}

@Preview(showBackground = false)
@Composable
fun inputPreview() {
    IskraTextField(
        value = "",
        onValueChange = {},
        label = "Имя",
        modifier = Modifier
    )
}