package com.niderkwel.iskra_mobile.components.screens.login

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.niderkwel.domain.API
import com.niderkwel.domain.models.LoginForm
import com.niderkwel.iskra_mobile.components.Screen
import com.niderkwel.iskra_mobile.components.UiStatus
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModelFactory(private val sharedPreferences: SharedPreferences) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        LoginViewModel(sharedPreferences) as T
}

class LoginViewModel(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private var job: Job? = null

    fun onEmailChanged(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    private fun saveUserId(id: Int) {
        sharedPreferences.edit().putInt("userId", id).apply()
    }

    fun login(navController: NavController) {
        with(_state.value) {
            if (email.isBlank() || password.isBlank()) return
        }

        job?.cancel()
        job = viewModelScope.launch {
            _state.value = _state.value.copy(uiStatus = UiStatus.Loading)

            runCatching {
                API.userService.login(
                    LoginForm(
                        password = state.value.password.trim(),
                        email = state.value.email.trim(),
                    )
                )
            }
                .onFailure {
                    _state.value = _state.value.copy(
                        uiStatus = UiStatus.Error("Что-то пошло не так")
                    )
                }
                .onSuccess { response ->
                    if (response.isSuccessful) {
                        if (response.body() == null) {
                            _state.value = _state.value.copy(
                                uiStatus = UiStatus.Error("Неверные почта или пароль")
                            )
                        } else {
                            val user = response.body()!!
                            if (user.isBlocked || user.isDeleted) {
                                _state.value = _state.value.copy(
                                    uiStatus = UiStatus.Error("Пользователь заблокирован или удален")
                                )
                            } else {
                                _state.value = _state.value.copy(uiStatus = UiStatus.Success)
                                saveUserId(response.body()!!.id)
                                navController.navigate(route = Screen.Main.path)
                            }
                        }
                    } else {
                        _state.value = _state.value.copy(
                            uiStatus = UiStatus.Error("Что-то пошло не так")
                        )
                    }
                }

        }
    }

    fun navigateToRegistration(navController: NavController) = viewModelScope.launch {
        navController.popBackStack()
        navController.navigate(route = Screen.Registration.path)
    }
}