package com.niderkwel.iskra_mobile.components.screens.event.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.niderkwel.iskra_mobile.R
import com.niderkwel.iskra_mobile.components.UiStatus
import com.niderkwel.iskra_mobile.components.ui.ErrorMessage
import com.niderkwel.iskra_mobile.components.ui.IskraCircularProgressBar
import com.niderkwel.iskra_mobile.dateString

@Composable
fun EventDetailScreen(
    state: State<EventDetailsState>,
    onRefresh: () -> Unit,
    onToggleAttendance: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
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
                state.value.event?.let { event ->
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.displaySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 16.dp)
                    ) {
                        Text(
                            text = event.author.name,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.icon_group),
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = "Участники"
                        )

                        Text(
                            text = "${event.members.size}",
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }

                    Text(
                        text = "${dateString(event.startDate)} / ${dateString(event.endDate)}",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelLarge,
                    )

                    Text(
                        text = event.description,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = { onToggleAttendance() },
                        enabled = state.value.toggle != UiStatus.Loading,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        if (state.value.toggle == UiStatus.Loading) {
                            IskraCircularProgressBar(
                                size = 20.dp,
                                strokeWidth = 3.dp
                            )
                        } else Text(
                            if (event.members.any { it.id == state.value.userId }) "Выйти"
                            else "Принять участие"
                        )
                    }
                }

                if (state.value.event == null)
                    Text(text = "Подобного мероприятия не нашлось")
            }

            is UiStatus.Error -> {
                Spacer(modifier = Modifier.weight(1f))
                ErrorMessage()
                Spacer(modifier = Modifier.height(16.dp))
                FilledTonalButton(onClick = onRefresh) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_refresh),
                        contentDescription = "обновить"
                    )
                    Text(text = "Обновить")
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}