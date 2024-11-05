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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.niderkwel.domain.models.Event
import com.niderkwel.domain.models.User
import com.niderkwel.iskra_mobile.R
import com.niderkwel.iskra_mobile.dateString
import java.time.LocalDateTime

@Composable
fun EventCard(
    event: Event,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = event.author.name,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f).padding(end = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelMedium,
            )

            Text(
                text = "${dateString(event.startDate)} / ${dateString(event.endDate)}",
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelMedium,
            )
        }

        Text(
            text = event.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 8.dp),
        )

        Text(
            text = event.description,
            textAlign = TextAlign.Justify,
            maxLines = 3,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp),
            overflow = TextOverflow.Ellipsis,
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = 8.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_group),
                contentDescription = "группа",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = "${event.members.size} участников",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun show() {
    EventCard(
        event = Event(
            id = 5,
            description = "Маленькое описание этого события Маленькое описание этого поста Маленькое описание этого поста Маленькое описание этого поста Маленькое описание этого поста Маленькое описание этого поста",
            title = "Заголовок Заголовок Заголовок Заголовок Заголовок",
            members = emptyList(),
            author = User(
                id = 5,
                email = "d",
                password = "f",
                name = "Имя Человекааааааааааааааааааааааааааааааааааааааааааа",
                photoUrl = "photo_placeholder",
                isBlocked = true,
                isDeleted = true
            ),
            endDate = LocalDateTime.of(2024, 2, 1, 9, 30),
            startDate = LocalDateTime.of(2024, 5, 1, 9, 30)
        ),
        onClick = {}
    )
}