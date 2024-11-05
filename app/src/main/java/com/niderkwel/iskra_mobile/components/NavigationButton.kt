package com.niderkwel.iskra_mobile.components

import androidx.annotation.DrawableRes

data class NavigationButton(
    @DrawableRes val icon: Int,
    @DrawableRes val selectedIcon: Int,
    val route: String,
    val label: String,
)
