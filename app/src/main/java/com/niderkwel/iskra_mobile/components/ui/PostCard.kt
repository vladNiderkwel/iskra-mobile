package com.niderkwel.iskra_mobile.components.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.niderkwel.domain.API_URL
import com.niderkwel.domain.models.Post
import com.niderkwel.domain.models.Staff
import com.niderkwel.iskra_mobile.R
import com.niderkwel.iskra_mobile.dateString
import java.time.LocalDateTime

@Composable
fun PostCard(
    post: Post,
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
    ) {

        if (post.photoUrl.length < 64) {
            Image(
                painter = painterResource(
                    id = R.drawable.post_placeholder
                ),
                contentDescription = "Картинка",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(MaterialTheme.shapes.large)
            )
        } else {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${API_URL}/images/posts/${post.photoUrl}.jpg")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.post_placeholder),
                contentDescription = "Картинка",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(MaterialTheme.shapes.large)
            )
        }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = post.author.name,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelMedium,
                )

                Text(
                    text = dateString(post.publicationDate),
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelMedium,
                )
            }

            Text(
                text = post.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp),
            )

            Text(
                text = post.description,
                textAlign = TextAlign.Justify,
                maxLines = 3,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview
@Composable
private fun show() {
    PostCard(
        post = Post(
            id = -1,
            title = "Заголовок Заголовок Заголовок Заголовок Заголовок",
            description = "Маленькое описание этого поста Маленькое описание этого поста Маленькое описание этого поста Маленькое описание этого поста Маленькое описание этого поста Маленькое описание этого постаМаленькое описание этого поста",
            publicationDate = LocalDateTime.now(),
            body = "",
            photoUrl = "",
            author = Staff(
                id = -1,
                name = "Имя Тестера",
                email = "d@d.d",
                password = ""
            )
        ),
        onClick = {}
    )
}