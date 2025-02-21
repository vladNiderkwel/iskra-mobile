package com.niderkwel.iskra_mobile.components.screens.task.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.niderkwel.domain.models.Option
import com.niderkwel.iskra_mobile.components.UiStatus
import com.niderkwel.iskra_mobile.components.ui.IskraCircularProgressBar
import com.niderkwel.iskra_mobile.components.ui.tasks.CheckboxSubtask
import com.niderkwel.iskra_mobile.components.ui.tasks.RadiobuttonSubtask
import com.niderkwel.iskra_mobile.components.ui.tasks.WriteSubtask

@Composable
fun TaskDetailsScreen(
    state: State<TaskDetailsState>,
    onWrittenAnswerChange: (id: Int, value: String) -> Unit,
    onCheckboxAnswerChange: (id: Int, value: Option, bool: Boolean) -> Unit,
    onRadiobuttonAnswerChange: (id: Int, value: Option) -> Unit,
    onSubmit: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        state.value.task?.let { task ->
            Text(
                text = task.title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall,
            )

            Text(
                text = "${task.reward} опт.",
                style = MaterialTheme.typography.labelLarge,
            )

            state.value.userTask?.answers?.forEachIndexed { index, userAnswer ->
                when (userAnswer.subtask.type) {
                    0 -> {
                        CheckboxSubtask(
                            subtask = userAnswer.subtask,
                            value = userAnswer.answers,
                            onValueChange = { option, bool ->
                                onCheckboxAnswerChange(userAnswer.id, option, bool)
                            }
                        )
                    }

                    1 -> {
                        RadiobuttonSubtask(
                            subtask = userAnswer.subtask,
                            value = userAnswer.answers,
                            onValueChange = { onRadiobuttonAnswerChange(userAnswer.id, it) }
                        )
                    }

                    2 -> {
                        WriteSubtask(
                            subtask = userAnswer.subtask,
                            value = userAnswer.writtenAnswer,
                            onValueChange = { onWrittenAnswerChange(userAnswer.id, it) }
                        )
                    }
                }

                if (index < task.subtasks.size - 1) HorizontalDivider()
            }

            Button(
                onClick = { onSubmit() },
                enabled = state.value.saving != UiStatus.Loading,
                modifier = Modifier.fillMaxWidth()
            ) {

                if (state.value.saving == UiStatus.Loading) {
                    IskraCircularProgressBar(
                        size = 20.dp,
                        strokeWidth = 3.dp
                    )
                } else Text("Сохранить ответы")
            }
        }
    }
}