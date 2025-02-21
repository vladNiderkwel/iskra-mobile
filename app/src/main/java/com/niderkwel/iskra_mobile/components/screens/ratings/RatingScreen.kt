package com.niderkwel.iskra_mobile.components.screens.ratings

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.niderkwel.iskra_mobile.components.UiStatus
import com.niderkwel.iskra_mobile.components.ui.IskraCircularProgressBar
import com.niderkwel.iskra_mobile.components.ui.LevelCard

@Composable
fun RatingScreen(
    state: State<RatingState>,
) {
    val lvlSize = remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "shimmer")
    val progressAnimated by transition.animateFloat(
        initialValue = -1000f,
        targetValue = lvlSize.value.width.toFloat() + 1000,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 3000,
                easing = CubicBezierEasing(0.0f, 0.1f, 0.9f, 1.0f)
            ),
            repeatMode = RepeatMode.Reverse
        ), label = "shimmer"
    )
    val brush = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.secondary,
            MaterialTheme.colorScheme.primary,
        ),
        start = Offset.Zero,
        end = Offset(progressAnimated, progressAnimated),
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        if (state.value.uiStatus == UiStatus.Loading)
            IskraCircularProgressBar(
                modifier = Modifier.align(
                    alignment = Alignment.CenterHorizontally
                )
            )

        state.value.levels.forEachIndexed { i, level ->
            LevelCard(
                level = level,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { l ->
                        lvlSize.value = l.size
                    },
                brush = brush
            )

            if (i < state.value.levels.size - 1)
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
        }
    }
}