package com.niderkwel.iskra_mobile.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.niderkwel.iskra_mobile.R
import com.niderkwel.iskra_mobile.components.Screen
import com.niderkwel.iskra_mobile.components.title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IskraTopBar(
    current: String,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onProfile: () -> Unit,
    onBack: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title(current),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { onBack() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_back),
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = "назад"
                )
            }
        },
        actions = {
            IconButton(
                onClick = { onProfile() },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(
                        if (current == Screen.Profile.path) MaterialTheme.colorScheme.secondaryContainer
                        else Color.Transparent
                    )
            ) {
                Icon(
                    painter = painterResource(
                        id = if (current == Screen.Profile.path) R.drawable.icon_filled_profile
                        else R.drawable.icon_profile
                    ),
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = "Профиль",
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}