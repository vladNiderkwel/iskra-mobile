package com.niderkwel.iskra_mobile.components.screens.questions.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.niderkwel.iskra_mobile.components.UiStatus
import com.niderkwel.iskra_mobile.components.ui.IskraCircularProgressBar
import com.niderkwel.iskra_mobile.components.ui.IskraTextField

@Composable
fun CreateQuestionScreen(
    state: State<CreateQuestionState>,
    onQuestionChanged: (String) -> Unit,
    onSave: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        ),
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Text(
            text = "Задать вопрос",
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        IskraTextField(
            value = state.value.question,
            onValueChange = { onQuestionChanged(it) },
            label = "Вопрос",
            maxLines = 7
        )

        Button(
            enabled = state.value.uiStatus != UiStatus.Loading,
            onClick = { onSave() }
        ) {
            if (state.value.uiStatus == UiStatus.Loading) {
                IskraCircularProgressBar(
                    size = 20.dp,
                    strokeWidth = 3.dp
                )
            } else Text("Задать")
        }
    }
}