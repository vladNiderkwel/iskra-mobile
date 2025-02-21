package com.niderkwel.iskra_mobile.components.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun IskraCircularProgressBar(
    strokeWidth: Dp = 8.dp,
    size: Dp = 64.dp,
    modifier: Modifier = Modifier,
) {
    CircularProgressIndicator(
        color = MaterialTheme.colorScheme.secondary,
        strokeWidth = strokeWidth,
        strokeCap  = StrokeCap.Round,
        modifier = modifier
            .size(size)
    )
}