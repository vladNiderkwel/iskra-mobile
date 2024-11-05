package com.niderkwel.iskra_mobile.components.screens.questions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.niderkwel.iskra_mobile.components.ui.QuestionCard

@Composable
fun QuestionsScreen(
    state: State<QuestionsState>,
    onClick: (Int) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {

        if (state.value.questions.isEmpty())
            Text(text = "Вопросов пока нет")

        state.value.questions.forEach { q ->
            QuestionCard(
                question = q,
                onClick = { onClick(q.id) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}