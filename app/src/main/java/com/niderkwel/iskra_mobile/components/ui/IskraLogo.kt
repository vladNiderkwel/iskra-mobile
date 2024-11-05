package com.niderkwel.iskra_mobile.components.ui

import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.niderkwel.iskra_mobile.R

@Composable
fun IskraLogo(
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = R.drawable.spark),
        contentDescription = "Логотип",

        colorFilter = ColorFilter.tint(
            MaterialTheme.colorScheme.secondary
        ),
        modifier = modifier
    )
}

@Preview
@Composable
fun icon() {
    IskraLogo()
}