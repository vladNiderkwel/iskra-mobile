package com.niderkwel.iskra_mobile.components.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun ShimmerBrush(
    colors: List<Color> = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary
    ),
    start: Offset = Offset.Zero,
    end: Offset = Offset.Infinite
): Brush =
    Brush.linearGradient(
        colors = colors,
        start = start,
        end = end
    )