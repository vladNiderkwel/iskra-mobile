package com.niderkwel.iskra_mobile.components.navigation.graphs

import android.content.ContentResolver
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.niderkwel.iskra_mobile.components.Screen
import com.niderkwel.iskra_mobile.components.screens.MainScreen

@Composable
fun RootNavGraph(
    navController: NavHostController,
    sharedPreferences: SharedPreferences,
    contentResolver: ContentResolver
) {
    NavHost(
        navController = navController,
        route = Screen.Root.path,
        startDestination = Screen.Auth.path
    ) {
        initAuthGraph(navController, sharedPreferences)
        composable(route = Screen.Main.path) {
            MainScreen(
                navController = navController,
                sharedPreferences = sharedPreferences,
                contentResolver = contentResolver
            )
        }
    }
}