package com.niderkwel.iskra_mobile.components.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicatorDefaults
import eu.bambooapps.material3.pullrefresh.PullRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IskraPullRefreshIndicator(
    refreshing: Boolean,
    pullState: PullRefreshState,
    modifier: Modifier = Modifier,
) {
    PullRefreshIndicator(
        refreshing = refreshing,
        state = pullState,
        modifier = modifier,
        shadowElevation = 4.dp,
        tonalElevation = 1.dp,
        colors = PullRefreshIndicatorDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.secondary
        ),
    )
}