package com.niderkwel.iskra_mobile.components.screens.profile

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.niderkwel.domain.API_URL
import com.niderkwel.iskra_mobile.R
import com.niderkwel.iskra_mobile.components.UiStatus
import com.niderkwel.iskra_mobile.components.ui.ErrorMessage
import com.niderkwel.iskra_mobile.components.ui.tasks.UserTaskCard

@Composable
fun ProfileScreen(
    state: State<ProfileState>,
    onEditProfile: () -> Unit,
    onQuestions: () -> Unit,
) {
    val lvlSize = remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "shimmer")
    val progressAnimated by transition.animateFloat(
        initialValue = -1000f,
        targetValue = lvlSize.value.width.toFloat() + 1000,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = CubicBezierEasing(0.0f, 0.1f, 0.9f, 1.0f)
            ),
            repeatMode = RepeatMode.Reverse
        ), label = "shimmer"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        state.value.user?.let {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                if (it.photoUrl.length < 64)
                    Image(
                        painter = painterResource(
                            id = R.drawable.photo_placeholder
                        ),
                        contentDescription = "Аватар",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                    )
                else
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("${API_URL}/images/photos/${it.photoUrl}.jpg")
                            .crossfade(true)
                            .memoryCachePolicy(CachePolicy.DISABLED)
                            .build(),
                        placeholder = painterResource(R.drawable.photo_placeholder),
                        contentDescription = "Аватар",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                    )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = { onQuestions() },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                            )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_questions),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            contentDescription = "Вопросы"
                        )
                    }

                    IconButton(
                        onClick = { onEditProfile() },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                            )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_edit),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            contentDescription = "Редактирвоание аккаунта"
                        )
                    }
                }
            }

            Text(
                text = it.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = it.email,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.surfaceTint,
                modifier = Modifier.fillMaxWidth()
            )

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            state.value.level?.let { level ->

                val need = level.current * 100f
                val have = need - level.expToNext

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = "${level.current} уровень",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.secondary,
                                        MaterialTheme.colorScheme.primary,
                                    ),
                                    start = Offset.Zero,
                                    end = Offset(progressAnimated, progressAnimated),
                                ),
                            ),
                            modifier = Modifier.onGloballyPositioned {l ->
                                lvlSize.value = l.size
                            }
                        )

                        Text(
                            text = "${level.expToNext} опт. до сдел. уровня",
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }

                    CircularProgressIndicator(
                        progress = { have / need },
                        strokeCap = StrokeCap.Butt,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        color = MaterialTheme.colorScheme.secondary,
                        strokeWidth = 12.dp,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            if (state.value.userTasks.isEmpty())
                Text(
                    textAlign = TextAlign.Center,
                    text = "Выполненных заданий пока нет",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.fillMaxWidth()
                )
            else {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Выполненные задания",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                state.value.userTasks.forEach { userTask ->
                    UserTaskCard(
                        userTask = userTask,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                }
            }
        }
    }

    if (state.value.uiStatus is UiStatus.Error) {
        ErrorMessage()
    }
}