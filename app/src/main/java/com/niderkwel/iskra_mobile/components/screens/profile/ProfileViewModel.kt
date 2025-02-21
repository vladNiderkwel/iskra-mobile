package com.niderkwel.iskra_mobile.components.screens.profile

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.niderkwel.domain.API
import com.niderkwel.iskra_mobile.components.UiStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModelFactory(private val sharedPreferences: SharedPreferences) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ProfileViewModel(sharedPreferences) as T
}

class ProfileViewModel(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    private var job: Job? = null

    init {
        fetchUserInfo()
    }

    fun fetchUserInfo() {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(uiStatus = UiStatus.Loading)

            val id = getUserId()

            kotlin.runCatching {
                fetchUser(id)
                fetchLevel(id)
                fetchUserTask(id)
            }
                .onFailure {
                    _state.value = _state.value.copy(
                        uiStatus = UiStatus.Error("Что-то пошло не так")
                    )
                }
                .onSuccess {
                    _state.value = _state.value.copy(
                        uiStatus = UiStatus.Success
                    )
                }

        }
    }

    private fun getUserId(): Int =
        sharedPreferences.getInt("userId", 0)

    private suspend fun fetchUser(id: Int) {
        kotlin.runCatching { API.userService.get(id) }
            .onSuccess { response ->
                if (response.isSuccessful) {
                    _state.value = _state.value.copy(
                        user = response.body()
                    )
                }
            }
    }

    private suspend fun fetchUserTask(id: Int) {
        kotlin.runCatching { API.userTaskService.allOfUser(id) }
            .onSuccess { response ->
                if (response.isSuccessful) {
                    _state.value = _state.value.copy(
                        userTasks = response.body() ?: emptyList()
                    )
                }
            }
    }

    private suspend fun fetchLevel(id: Int) {
        kotlin.runCatching { API.userService.level(id) }
            .onSuccess { response ->
                if (response.isSuccessful) {
                    _state.value = _state.value.copy(
                        level = response.body()
                    )
                }
            }
    }
}