package com.niderkwel.iskra_mobile.components.screens.questions.details

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

class QuestionDetailsViewModelFactory(
    private val id: Int,
    //private val sharedPreferences: SharedPreferences
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        QuestionDetailsViewModel(id,
            //sharedPreferences
        ) as T
}

class QuestionDetailsViewModel(
    private val id: Int,
    //private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _state = MutableStateFlow(QuestionDetailsState())
    val state = _state.asStateFlow()

    private var job: Job? = null

    init {
        fetchDetail()
    }

    fun fetchDetail() {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(uiStatus = UiStatus.Loading)

            val response = API.questionService.get(id)
            if (response.isSuccessful) {
                _state.value = _state.value.copy(
                    uiStatus = UiStatus.Success,
                    question = response.body()
                )
            } else
                _state.value =
                    _state.value.copy(uiStatus = UiStatus.Error("Что-то пошло не так"))
        }
    }
}