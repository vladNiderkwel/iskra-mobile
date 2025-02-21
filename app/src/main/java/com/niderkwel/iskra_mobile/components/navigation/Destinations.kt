package com.niderkwel.iskra_mobile.components.navigation

import com.niderkwel.iskra_mobile.R
import com.niderkwel.iskra_mobile.components.NavigationButton
import com.niderkwel.iskra_mobile.components.Screen

val DESTINATIONS = listOf(
    NavigationButton(
        icon = R.drawable.icon_event,
        selectedIcon = R.drawable.icon_filled_event,
        route = Screen.Events.path,
        label = "События"
    ),
    NavigationButton(
        icon = R.drawable.icon_map,
        selectedIcon = R.drawable.icon_filled_map,
        route = Screen.Map.path,
        label = "Карта"
    ),
    NavigationButton(
        icon = R.drawable.icon_star,
        selectedIcon = R.drawable.icon_filled_star,
        route = Screen.Ratings.path,
        label = "Рейтинг"
    ),
    NavigationButton(
        icon = R.drawable.icon_task,
        selectedIcon = R.drawable.icon_filled_task,
        route = Screen.Tasks.path,
        label = "Задания"
    ),
    NavigationButton(
        icon = R.drawable.icon_post,
        selectedIcon = R.drawable.icon_filled_post,
        route = Screen.Posts.path,
        label = "Посты"
    ),
)