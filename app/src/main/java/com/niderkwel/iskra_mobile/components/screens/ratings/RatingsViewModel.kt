package com.niderkwel.iskra_mobile.components.screens.ratings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niderkwel.domain.API
import com.niderkwel.iskra_mobile.components.UiStatus
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class RatingsViewModel : ViewModel() {
    private val _state = MutableStateFlow(RatingState())
    val state = _state.asStateFlow()

    private var job: Job? = null

    init {
        fetchRatings()
    }

    fun fetchRatings() {
        job?.cancel()
        job = viewModelScope.launch {
            _state.value = _state.value.copy(uiStatus = UiStatus.Loading)

            kotlin.runCatching { API.userService.ratings() }
                .onSuccess { response ->
                    if (response.isSuccessful) {
                        _state.value = _state.value.copy(
                            uiStatus = UiStatus.Success,
                            levels = response.body()?: emptyList()
                        )
                    }
                }
                .onFailure {
                    _state.value = _state.value.copy(
                        uiStatus = UiStatus.Error("Что-то пошло не так")
                    )
                }
        }
    }
}