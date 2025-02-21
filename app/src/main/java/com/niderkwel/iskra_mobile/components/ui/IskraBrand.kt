package com.niderkwel.iskra_mobile.components.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IskraBrand(
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement
            .spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterHorizontally
            ),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        IskraLogo(
            modifier = Modifier.size(32.dp),
        )

        Text(
            text = "ИСКРА",
            fontSize = 20.sp,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary,
        )
    }
}