package com.niderkwel.iskra_mobile.components.screens.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.niderkwel.iskra_mobile.R
import com.niderkwel.iskra_mobile.components.UiStatus
import com.niderkwel.iskra_mobile.components.ui.ErrorMessage
import com.niderkwel.iskra_mobile.components.ui.IskraCircularProgressBar
import com.niderkwel.iskra_mobile.components.ui.OsmdroidMapView

@Composable
fun MapScreen(
    state: State<MapState>,
    onRefresh: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        OsmdroidMapView(LocalContext.current, state.value.marks)

        when (state.value.uiStatus) {
            UiStatus.Loading -> {
                IskraCircularProgressBar()
            }

            is UiStatus.Error -> {
                ErrorMessage()

                FilledTonalButton(onClick = onRefresh) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_refresh),
                        contentDescription = "обновить"
                    )

                    Text(text = "Обновить")
                }
            }

            UiStatus.Success -> {}
        }
    }
}