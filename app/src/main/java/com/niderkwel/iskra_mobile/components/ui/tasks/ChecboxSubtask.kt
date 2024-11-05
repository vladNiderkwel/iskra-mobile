package com.niderkwel.iskra_mobile.components.ui.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.niderkwel.domain.models.Option
import com.niderkwel.domain.models.Subtask

@Composable
fun CheckboxSubtask(
    subtask: Subtask,
    value: List<Option>,
    onValueChange: (Option, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement
            .spacedBy(space = 8.dp),
        modifier = modifier.padding(vertical = 16.dp)
    ) {
        Text(
            text = subtask.question,
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.fillMaxWidth()
        )

        subtask.options.forEachIndexed { index, option ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.large)
                    .padding(vertical = 4.dp)
            ) {
                Checkbox(
                    checked = value.any { it.id == option.id },
                    onCheckedChange = {
                        onValueChange(option, it)
                    },
                )

                Text(
                    text = option.text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                )
            }

            if (index < subtask.options.size - 1)
                HorizontalDivider()
        }
    }
}

@Preview
@Composable
private fun show() {
    CheckboxSubtask(
        value = emptyList(),
        onValueChange = { o, i -> },
        subtask = Subtask(
            question = "Ответишь?",
            options = listOf(
                Option(
                    id = 1,
                    text = "fdsfsdfdf"
                ),
                Option(
                    id = 1,
                    text = "fdsfsdfdf"
                ),
                Option(
                    id = 1,
                    text = "fdsfsdfdf"
                ),
                Option(
                    id = 1,
                    text = "fdsfsdfdf"
                )
            ),
            type = 0
        )
    )
}