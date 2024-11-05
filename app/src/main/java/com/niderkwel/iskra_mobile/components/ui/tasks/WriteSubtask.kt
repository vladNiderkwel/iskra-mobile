package com.niderkwel.iskra_mobile.components.ui.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.niderkwel.domain.models.Subtask
import com.niderkwel.iskra_mobile.components.ui.IskraTextField

@Composable
fun WriteSubtask(
    subtask: Subtask,
    value: String = "",
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement
            .spacedBy(space = 16.dp),
        modifier = modifier.padding(vertical = 16.dp)

    ) {
        Text(
            text = subtask.question,
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier.fillMaxWidth()
        )

        IskraTextField(
            value = value,
            onValueChange = onValueChange,
            maxLines = 5,
            modifier = modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun show() {
    WriteSubtask(
        value = "Мой ответ",
        onValueChange = {},
        subtask = Subtask(
            question = "Ответишь?",
            options = emptyList(),
            type = 2
        )
    )
}