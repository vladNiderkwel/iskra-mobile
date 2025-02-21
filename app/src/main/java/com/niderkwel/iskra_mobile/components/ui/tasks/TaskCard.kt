package com.niderkwel.iskra_mobile.components.ui.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.niderkwel.domain.models.Subtask
import com.niderkwel.domain.models.Task
import com.niderkwel.iskra_mobile.R

@Composable
fun TaskCard(
    task: Task,
    isDone: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val subtasks = task.subtasks.size

    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically
        ),
        modifier = modifier
            .border(
                color = if (isDone) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.outlineVariant,
                width = 1.dp,
                shape = MaterialTheme.shapes.large
            )
            .clip(MaterialTheme.shapes.large)
            .background(
                color = MaterialTheme.colorScheme.surface
            )
            .clickable { if (!isDone) onClick() }
            .padding(16.dp)
    ) {
        if (isDone) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_filled_done),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Задание выполнено"
                )

                Text(
                    text = "Выполнено",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Text(
            text = task.title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (subtasks == 1) "$subtasks подзадание"
                else "$subtasks подзаданий",
                maxLines = 1,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Text(
                text = "${task.reward} опыта",
                maxLines = 1,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Preview
@Composable
private fun show() {
    TaskCard(
        task = Task(
            id = -1,
            title = "Съешь камень",
            available = true,
            reward = 1500,
            subtasks = listOf(
                Subtask(
                    id = -1,
                    type = 1,
                    options = emptyList(),
                    question = "Как?"
                )
            )
        ),
        isDone = true,
        onClick = { }
    )
}