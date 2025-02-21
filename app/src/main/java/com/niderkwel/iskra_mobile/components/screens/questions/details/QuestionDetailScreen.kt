package com.niderkwel.iskra_mobile.components.screens.questions.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.niderkwel.iskra_mobile.R
import com.niderkwel.iskra_mobile.components.UiStatus
import com.niderkwel.iskra_mobile.components.ui.ErrorMessage
import com.niderkwel.iskra_mobile.components.ui.IskraCircularProgressBar

@Composable
fun QuestionDetailScreen(
    state: State<QuestionDetailsState>,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        when (state.value.uiStatus) {
            UiStatus.Loading -> {
                Spacer(modifier = Modifier.weight(1f))

                IskraCircularProgressBar()

                Spacer(modifier = Modifier.weight(1f))
            }

            UiStatus.Success -> {
                state.value.question?.let { q ->
                    Text(
                        text = q.question,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                    )

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if(q.phase.toInt() == 0) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_wait),
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = "Ожидание"
                            )

                            Text(
                                text = "Ждет ответа",
                                color = MaterialTheme.colorScheme.secondary,
                                style = MaterialTheme.typography.labelLarge,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                        else {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_answered),
                                tint = MaterialTheme.colorScheme.primary,
                                contentDescription = "Ответ дан"
                            )

                            Text(
                                text = "Ответ дан",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }

                    Text(
                        text = q.answer,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                if (state.value.question == null)
                    Text(text = "Подобного вопроса не нашлось")
            }

            is UiStatus.Error -> ErrorMessage()
        }
    }
}