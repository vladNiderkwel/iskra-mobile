package com.niderkwel.iskra_mobile.components.screens.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.niderkwel.iskra_mobile.components.UiStatus
import com.niderkwel.iskra_mobile.components.ui.ErrorMessage
import com.niderkwel.iskra_mobile.components.ui.EventCard
import com.niderkwel.iskra_mobile.components.ui.IskraPullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(
    state: State<EventsState>,
    onRefresh: () -> Unit,
    onCreateEvent: () -> Unit,
    seeDetails: (id: Int) -> Unit,
) {
    val pullState = rememberPullRefreshState(
        refreshing = state.value.uiStatus == UiStatus.Loading,
        onRefresh = onRefresh
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .pullRefresh(state = pullState)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 8.dp),
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        ) {
            items(state.value.events) { event ->
                EventCard(
                    event = event,
                    onClick = { seeDetails(event.id) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }

        if (state.value.uiStatus is UiStatus.Error) {
            ErrorMessage(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        IskraPullRefreshIndicator(
            refreshing = state.value.uiStatus == UiStatus.Loading,
            pullState = pullState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun show() {
    val viewModel = viewModel<EventsViewModel>()
    EventsScreen(
        state = viewModel.state.collectAsState(),
        seeDetails = {},
        onRefresh = {},
        onCreateEvent = {}
    )
}