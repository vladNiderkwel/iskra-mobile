package com.niderkwel.iskra_mobile.components.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.niderkwel.iskra_mobile.components.NavigationButton
import com.niderkwel.iskra_mobile.components.Screen

@Composable
fun IskraNavigationBar(
    current: String,
    navController: NavHostController,
    links: List<NavigationButton> = DESTINATIONS,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        HorizontalDivider()

        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background,
            tonalElevation = 0.dp,
        ) {
            links.forEach { item ->
                val selected = item.route.contains(current)

                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        navController.popBackStack()
                        navController.navigate(item.route)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(
                                id = if (selected) item.selectedIcon
                                else item.icon
                            ),
                            contentDescription = item.label
                        )
                    },
                    label = { Text(text = item.label) }
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun show() {
    IskraNavigationBar(
        current = Screen.Events.path,
        navController = NavHostController(LocalContext.current),
        links = DESTINATIONS,
    )
}
