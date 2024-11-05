package com.niderkwel.iskra_mobile.components.screens.post.details

import androidx.compose.foundation.Image
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
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import com.niderkwel.iskra_mobile.R
import com.niderkwel.iskra_mobile.components.UiStatus
import com.niderkwel.iskra_mobile.components.ui.ErrorMessage
import com.niderkwel.iskra_mobile.components.ui.Html
import com.niderkwel.iskra_mobile.components.ui.IskraCircularProgressBar

@Composable
fun PostDetailsScreen(
    state: State<PostDetailsState>,
    onRefresh: () -> Unit,
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
                state.value.post?.let {

                    Text(
                        text = it.title,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (it.photoUrl.length < 64)
                        Image(
                            painter = painterResource(
                                id = R.drawable.post_placeholder
                            ),
                            contentDescription = "Картинка",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(MaterialTheme.shapes.extraLarge)
                        )
                    else
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("${API_URL}/images/posts/${it.photoUrl}.jpg")
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(
                                id = R.drawable.post_placeholder
                            ),
                            contentDescription = "Картинка",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(MaterialTheme.shapes.extraLarge)
                        )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = it.author.name,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.weight(1f).padding(end = 8.dp),
                            maxLines = 1,
                            style = MaterialTheme.typography.labelLarge,
                            overflow = TextOverflow.Ellipsis,
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.icon_eye),
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = "Просмотры"
                        )

                        Text(
                            text = "${it.views}",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }

                    Text(
                        text = it.description,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )

                    HorizontalDivider()

                    Html(
                        html = it.body,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

                if (state.value.post == null)
                    Text(
                        text = "Подобного поста не нашлось",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium,
                    )
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

@Preview(showBackground = true)
@Composable
private fun show() {
    PostDetailsScreen(
        state = remember { mutableStateOf(PostDetailsState()) },
        onRefresh = {}
    )
}