package com.niderkwel.iskra_mobile.components.screens.event.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niderkwel.iskra_mobile.components.UiStatus
import com.niderkwel.iskra_mobile.components.ui.IskraCircularProgressBar
import com.niderkwel.iskra_mobile.components.ui.IskraTextField
import com.niderkwel.iskra_mobile.dateString
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventScreen(
    state: State<CreateEventState>,
    onTitleChanged: (value: String) -> Unit,
    onDescriptionChanged: (value: String) -> Unit,
    onStartChanged: (value: LocalDateTime) -> Unit,
    onEndChanged: (value: LocalDateTime) -> Unit,
    onSave: () -> Unit,
) {
    val openStartDialog = remember { mutableStateOf(false) }
    val openEndDialog = remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        ),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Организация мероприятия",
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        IskraTextField(
            value = state.value.title,
            onValueChange = { onTitleChanged(it) },
            label = "Заголовок"
        )

        IskraTextField(
            value = state.value.description,
            onValueChange = { onDescriptionChanged(it) },
            label = "Описание",
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Начало ${dateString(state.value.start)}",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = { openStartDialog.value = true }
            ) {
                Icon(
                    imageVector = Icons.Rounded.DateRange,
                    contentDescription = "Выбор даты начала",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Конец ${dateString(state.value.end)}",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = { openEndDialog.value = true }
            ) {
                Icon(
                    imageVector = Icons.Rounded.DateRange,
                    contentDescription = "Выбор даты конца",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        if (openStartDialog.value) {
            val dateState = rememberDatePickerState()

            DatePickerDialog(
                onDismissRequest = { openStartDialog.value = false },
                confirmButton = {
                    dateState.selectedDateMillis?.let {
                        onStartChanged(
                            LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(it),
                                ZoneId.systemDefault()
                            )
                        )
                    }

                    Button(onClick = { openStartDialog.value = false }) {
                        Text("Сохранить")
                    }
                },
            ) {
                DatePicker(
                    state = dateState,
                    title = {},
                    headline = {
                        Text(
                            text = "Начало",
                            fontSize = 24.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                )
            }
        }

        if (openEndDialog.value) {
            val dateState = rememberDatePickerState()

            DatePickerDialog(
                onDismissRequest = { openEndDialog.value = false },
                confirmButton = {
                    dateState.selectedDateMillis?.let {
                        onEndChanged(
                            LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(it),
                                ZoneId.systemDefault()
                            )
                        )
                    }

                    Button(onClick = { openEndDialog.value = false }) {
                        Text("Сохранить")
                    }
                },
            ) {
                DatePicker(
                    state = dateState,
                    title = {},
                    headline = {
                        Text(
                            text = "Конец",
                            fontSize = 24.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                )
            }
        }

        Button(
            enabled = state.value.uiStatus != UiStatus.Loading,
            onClick = { onSave() }
        ) {
            if (state.value.uiStatus == UiStatus.Loading) {
                IskraCircularProgressBar(
                    size = 20.dp,
                    strokeWidth = 3.dp
                )
            } else Text("Добавить")
        }
    }
}