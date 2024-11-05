package com.niderkwel.iskra_mobile.components

sealed class Screen(val path: String) {
    data object Root : Screen(path = "root")
    data object Auth : Screen(path = "auth")
    data object Main : Screen(path = "main")

    data object Login : Screen(path = "login")
    data object Registration : Screen(path = "registration")
    data object Ratings : Screen(path = "ratings")
    data object Profile : Screen(path = "profile")
    data object ProfileEdit : Screen(path = "profile/edit")
    data object Tasks : Screen(path = "tasks")
    data object Task : Screen(path = "task/{id}")

    data object Posts : Screen(path = "posts")
    data object Post : Screen(path = "post/{id}")

    data object Questions : Screen(path = "questions")
    data object Question : Screen(path = "question/{id}")
    data object CreateQuestion : Screen(path = "question/create")

    data object Events : Screen(path = "events")
    data object CreateEvent : Screen(path = "event/create")
    data object Event : Screen(path = "event/{id}")

    data object Map : Screen(path = "map")
}

fun title(route: String): String = when (route) {
    Screen.Profile.path -> "Профиль"
    Screen.Posts.path -> "Посты"
    Screen.Events.path -> "События"
    Screen.Questions.path -> "Вопросы"
    Screen.Tasks.path -> "Задания"
    Screen.Map.path -> "Карта"
    Screen.Ratings.path -> "Рейтинг"
    else -> ""
}