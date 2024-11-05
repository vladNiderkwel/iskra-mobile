package com.niderkwel.iskra_mobile.components.screens.registration

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.niderkwel.domain.API
import com.niderkwel.domain.models.RegistrationForm
import com.niderkwel.iskra_mobile.components.Screen
import com.niderkwel.iskra_mobile.components.UiStatus
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistrationViewModelFactory(private val sharedPreferences: SharedPreferences) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        RegistrationViewModel(sharedPreferences) as T
}

class RegistrationViewModel(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(RegistrationState())
    val state = _state.asStateFlow()

    private var job: Job? = null

    fun onNameChanged(name: String) {
        _state.value = _state.value.copy(name = name)
    }

    fun onEmailChanged(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    fun onPasswordConfirmChanged(passwordConfirm: String) {
        _state.value = _state.value.copy(passwordConfirm = passwordConfirm)
    }

    private fun saveUserId(id: Int) {
        sharedPreferences.edit().putInt("userId", id).apply()
    }

    fun register(navController: NavController) {
        with(_state.value) {
            if (
                email.isBlank() || password.isBlank() ||
                passwordConfirm.isBlank() || name.isBlank()
            ) return

            if (password != passwordConfirm) return
        }

        job?.cancel()
        job = viewModelScope.launch {
            _state.value = _state.value.copy(uiStatus = UiStatus.Loading)

            runCatching {
                API.userService.create(
                    state.value.let {
                        RegistrationForm(
                            name = it.name,
                            email = it.email,
                            password = it.password
                        )
                    }
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
                                uiStatus = UiStatus.Error("Подобная почта уже используется")
                            )
                        } else {
                            _state.value = _state.value.copy(uiStatus = UiStatus.Success)
                            saveUserId(response.body()!!.id)
                            navController.navigate(route = Screen.Main.path)
                        }

                    } else {
                        _state.value = _state.value.copy(
                            uiStatus = UiStatus.Error("Что-то пошло не так")
                        )
                    }
                }
        }
    }

    fun navigateToLogin(navController: NavController) = viewModelScope.launch {
        navController.popBackStack()
        navController.navigate(route = Screen.Login.path)
    }


}