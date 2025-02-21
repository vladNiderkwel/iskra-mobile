package com.niderkwel.iskra_mobile.components.ui

import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.niderkwel.iskra_mobile.R

@Composable
fun RefreshButton(
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilledTonalButton(
        onClick = onRefresh,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_refresh),
            contentDescription = "обновить"
        )

        Text(text = "Обновить")
    }
}