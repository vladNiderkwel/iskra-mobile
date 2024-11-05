package com.niderkwel.iskra_mobile.components.ui.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.niderkwel.domain.models.UserTask

@Composable
fun UserTaskCard(
    userTask: UserTask,
    modifier: Modifier = Modifier,
) {
    val subtasks = userTask.task.subtasks.size

    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically
        ),
        modifier = modifier
            .border(
                color = MaterialTheme.colorScheme.outlineVariant,
                width = 1.dp,
                shape = MaterialTheme.shapes.large
            )
            .clip(MaterialTheme.shapes.large)
            .background(
                color = MaterialTheme.colorScheme.surface
            )
            .padding(16.dp)
    ) {
        Text(
            text = userTask.task.title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = if (subtasks == 1) "$subtasks подзадание"
            else "$subtasks подзаданий",
            maxLines = 1,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}