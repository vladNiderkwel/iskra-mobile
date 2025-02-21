package com.niderkwel.iskra_mobile.components.screens

import android.content.ContentResolver
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.niderkwel.iskra_mobile.components.Screen
import com.niderkwel.iskra_mobile.components.navigation.DESTINATIONS
import com.niderkwel.iskra_mobile.components.navigation.IskraNavigationBar
import com.niderkwel.iskra_mobile.components.navigation.IskraTopBar
import com.niderkwel.iskra_mobile.components.navigation.graphs.MainNavGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    sharedPreferences: SharedPreferences,
    contentResolver: ContentResolver
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val navControllerBottomBar = rememberNavController()

    var current by remember { mutableStateOf(Screen.Profile.path) }

    LaunchedEffect(navControllerBottomBar.currentBackStackEntryFlow) {
        navControllerBottomBar.currentBackStackEntryFlow.collect {
            current = it.destination.route ?: ""
        }
    }

    Scaffold(
        topBar = {
            IskraTopBar(
                current = current,
                scrollBehavior = if (current != Screen.Map.path) scrollBehavior else null,
                onProfile = {
                    navControllerBottomBar.navigate(Screen.Profile.path)
                },
                onBack = {
                    if (current.contains("/")) navControllerBottomBar.popBackStack()
                    else navController.popBackStack()
                }
            )
        },
        bottomBar = {
            IskraNavigationBar(
                current = current,
                navController = navControllerBottomBar,
                links = DESTINATIONS,
            )
        },
        floatingActionButton = {
            when (current) {
                Screen.Events.path -> FloatingActionButton(
                    onClick = { navControllerBottomBar.navigate(Screen.CreateEvent.path) }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Добавить событие"
                    )
                }
                Screen.Questions.path -> FloatingActionButton(
                    onClick = { navControllerBottomBar.navigate(Screen.CreateQuestion.path) }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Задать вопрос"
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = it.calculateBottomPadding(),
                    top = it.calculateTopPadding()
                )
        ) {
            navControllerBottomBar.addOnDestinationChangedListener { _, dest, _ ->
                current = dest.route ?: ""
            }

            MainNavGraph(
                navController = navControllerBottomBar,
                modifier = Modifier.align(Alignment.TopCenter),
                sharedPreferences = sharedPreferences,
                contentResolver = contentResolver
            )
        }
    }
}