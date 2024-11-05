package com.niderkwel.iskra_mobile.components.navigation.graphs

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.niderkwel.iskra_mobile.components.Screen
import com.niderkwel.iskra_mobile.components.screens.login.LoginScreen
import com.niderkwel.iskra_mobile.components.screens.login.LoginViewModel
import com.niderkwel.iskra_mobile.components.screens.login.LoginViewModelFactory
import com.niderkwel.iskra_mobile.components.screens.registration.RegistrationScreen
import com.niderkwel.iskra_mobile.components.screens.registration.RegistrationViewModel
import com.niderkwel.iskra_mobile.components.screens.registration.RegistrationViewModelFactory

@Composable
fun AuthNavGraph(
    navController: NavHostController,
    sharedPreferences: SharedPreferences
) {
    NavHost(
        navController = navController,
        route = Screen.Auth.path,
        startDestination = Screen.Registration.path
    ) {
        addRegistration(navController, sharedPreferences)
        addLogin(navController, sharedPreferences)
    }
}

fun NavGraphBuilder.initAuthGraph(
    navController: NavController,
    sharedPreferences: SharedPreferences
) {
    navigation(
        route = Screen.Auth.path,
        startDestination = Screen.Registration.path
    ) {
        addRegistration(navController, sharedPreferences)
        addLogin(navController, sharedPreferences)
    }
}

private fun NavGraphBuilder.addRegistration(
    navController: NavController,
    sharedPreferences: SharedPreferences
) {
    composable(route = Screen.Registration.path) {
        val viewModel = viewModel<RegistrationViewModel>(
            factory = RegistrationViewModelFactory(sharedPreferences)
        )
        RegistrationScreen(
            state = viewModel.state.collectAsState(),
            onNameChanged = { viewModel.onNameChanged(it) },
            onEmailChanged = { viewModel.onEmailChanged(it) },
            onPasswordChanged = { viewModel.onPasswordChanged(it) },
            onPasswordConfirmChanged = { viewModel.onPasswordConfirmChanged(it) },
            navigateToLogin = { viewModel.navigateToLogin(navController) },
            onRegistration = { viewModel.register(navController) }
        )
    }
}

private fun NavGraphBuilder.addLogin(
    navController: NavController,
    sharedPreferences: SharedPreferences
) {
    composable(route = Screen.Login.path) {
        val viewModel = viewModel<LoginViewModel>(
            factory = LoginViewModelFactory(sharedPreferences)
        )
        LoginScreen(
            state = viewModel.state.collectAsState(),
            onEmailChanged = { viewModel.onEmailChanged(it) },
            onPasswordChanged = { viewModel.onPasswordChanged(it) },
            navigateToRegistration = { viewModel.navigateToRegistration(navController) },
            onLogin = { viewModel.login(navController) }
        )
    }
}