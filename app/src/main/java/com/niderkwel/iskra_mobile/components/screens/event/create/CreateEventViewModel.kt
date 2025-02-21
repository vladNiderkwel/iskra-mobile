package com.niderkwel.iskra_mobile.components.screens.event.create

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.niderkwel.domain.API
import com.niderkwel.domain.models.Event
import com.niderkwel.iskra_mobile.components.UiStatus
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class CreateEventViewModelFactory(private val sharedPreferences: SharedPreferences) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CreateEventViewModel(sharedPreferences) as T
}

class CreateEventViewModel(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _state = MutableStateFlow(CreateEventState())
    val state = _state.asStateFlow()

    private var job: Job? = null

    fun onTitleChanged(value: String) {
        _state.value = _state.value.copy(title = value)
    }

    fun onDescriptionChanged(value: String) {
        _state.value = _state.value.copy(description = value)
    }

    fun onStartChanged(value: LocalDateTime) {
        _state.value = _state.value.copy(start = value)
    }

    fun onEndChanged(value: LocalDateTime) {
        _state.value = _state.value.copy(end = value)
    }

    fun onSave(navController: NavController) {

        _state.value.let {
            if (
                it.title.isBlank() ||
                it.description.isBlank() ||
                it.start.isAfter(it.end)
            ) return
        }

        val id = sharedPreferences.getInt("userId", -1)

        if (id == -1) return

        job?.cancel()
        job = viewModelScope.launch {
            _state.value = _state.value.copy(uiStatus = UiStatus.Loading)

            val user = API.userService.get(id).body()!!

            kotlin.runCatching {
                API.eventService.create(
                    _state.value.let {
                        Event(
                            id = -1,
                            title = it.title.trim(),
                            description = it.description.trim(),
                            startDate = it.start,
                            endDate = it.end,
                            members = listOf(user),
                            author = user
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
                        _state.value = _state.value.copy(
                            uiStatus = UiStatus.Success
                        )
                        navController.popBackStack()
                        navController.navigate("event/${response.body()!!}")
                    } else {
                        _state.value = _state.value.copy(
                            uiStatus = UiStatus.Error("Что-то пошло не так")
                        )
                    }
                }

        }
    }
}