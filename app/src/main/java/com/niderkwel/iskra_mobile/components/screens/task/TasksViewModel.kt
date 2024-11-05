package com.niderkwel.iskra_mobile.components.screens.task

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

class TasksViewModelFactory(private val sharedPreferences: SharedPreferences) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        TasksViewModel(sharedPreferences) as T
}

class TasksViewModel(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _state = MutableStateFlow(TasksState())
    val state = _state.asStateFlow()

    private var job: Job? = null

    init {
        fetchTasks()
    }

    fun fetchTasks() {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(uiStatus = UiStatus.Loading)

            runCatching { API.taskService.all() }
                .onFailure {
                    _state.value = _state.value.copy(
                        uiStatus = UiStatus.Error("Что-то пошло не так")
                    )
                }
                .onSuccess { response ->
                    if (response.isSuccessful) {
                        _state.value = _state.value.copy(
                            tasks = response.body()?.content ?: emptyList(),
                        )

                        fetchUserTask(getUserId())
                    } else _state.value = _state.value.copy(
                        uiStatus = UiStatus.Error("Что-то пошло не так")
                    )
                }
        }
    }

    private suspend fun fetchUserTask(id: Int) {
        kotlin.runCatching { API.userTaskService.allOfUser(id) }
            .onSuccess { response ->
                if (response.isSuccessful) {
                    _state.value = _state.value.copy(
                        uiStatus = UiStatus.Success,
                        userTasks = response.body() ?: emptyList()
                    )
                }
            }
    }

    private fun getUserId(): Int =
        sharedPreferences.getInt("userId", 0)
}