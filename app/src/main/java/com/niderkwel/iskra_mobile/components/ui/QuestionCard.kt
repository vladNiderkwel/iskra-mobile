package com.niderkwel.iskra_mobile.components.ui

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.niderkwel.domain.models.Question
import com.niderkwel.iskra_mobile.R

@Composable
fun QuestionCard(
    question: Question,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Column(
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
                .clickable { onClick() }
                .padding(16.dp)
        ) {
            Text(
                text = question.question,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp),
            )

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if(question.phase.toInt() == 0) {
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
        }
    }
}