package com.niderkwel.iskra_mobile.components.screens.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niderkwel.domain.API
import com.niderkwel.iskra_mobile.components.UiStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {

    private val _state = MutableStateFlow(MapState())
    val state = _state.asStateFlow()

    private var job: Job? = null

    init {
        fetchMapMarks()
    }

    fun fetchMapMarks() {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(uiStatus = UiStatus.Loading)

            runCatching {
                API.mapMarkService.all()
            }
                .onFailure {
                    _state.value = _state.value.copy(
                        uiStatus = UiStatus.Error("Что-то пошло не так")
                    )
                }
                .onSuccess { response ->
                    if (response.isSuccessful) {
                        _state.value = _state.value.copy(
                            uiStatus = UiStatus.Success,
                            marks = response.body() ?: emptyList()
                        )
                    } else _state.value = _state.value.copy(
                        uiStatus = UiStatus.Error("Что-то пошло не так")
                    )
                }
        }
    }
}