package com.niderkwel.iskra_mobile.components.screens.event.details

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

class EventDetailsViewModelFactory(
    private val id: Int,
    private val sharedPreferences: SharedPreferences
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        EventDetailsViewModel(id, sharedPreferences) as T
}

class EventDetailsViewModel(
    private val id: Int,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _state = MutableStateFlow(EventDetailsState())
    val state = _state.asStateFlow()

    private var job: Job? = null

    init {
        _state.value = _state.value.copy(
            userId = sharedPreferences.getInt("userId", -1)
        )
        fetchDetail()
    }

    fun fetchDetail() {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(uiStatus = UiStatus.Loading)

            val response = API.eventService.get(id)
            if (response.isSuccessful) {
                _state.value = _state.value.copy(
                    uiStatus = UiStatus.Success,
                    event = response.body()
                )
            } else
                _state.value =
                    _state.value.copy(uiStatus = UiStatus.Error("Что-то пошло не так"))
        }
    }

    fun onToggle() {
        val userId = sharedPreferences.getInt("userId", -1)

        if (userId == -1) return

        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(toggle = UiStatus.Loading)

            val response = API.eventService.toggleAttendance(id, userId)

            if (response.isSuccessful) {
                _state.value = _state.value.copy(toggle = UiStatus.Success)
                fetchDetail()
            } else
                _state.value =
                    _state.value.copy(toggle = UiStatus.Error("Что-то пошло не так"))
        }
    }
}