package com.niderkwel.iskra_mobile.components.screens.questions

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.niderkwel.domain.API
import com.niderkwel.iskra_mobile.components.UiStatus
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuestionsViewModelFactory(private val sharedPreferences: SharedPreferences) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        QuestionsViewModel(sharedPreferences) as T
}

class QuestionsViewModel(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _state = MutableStateFlow(QuestionsState())
    val state = _state.asStateFlow()

    private var job: Job? = null

    init {
        fetchQuestions()
    }

    fun fetchQuestions() {
        val userId = sharedPreferences.getInt("userId", -1)

        job?.cancel()
        job = viewModelScope.launch {
            _state.value = _state.value.copy(uiStatus = UiStatus.Loading)

            kotlin.runCatching { API.questionService.all() }
                .onSuccess { response ->
                    if (response.isSuccessful) {
                        _state.value = _state.value.copy(
                            uiStatus = UiStatus.Success,
                            questions =
                                response.body()?.content?.filter { e -> e.author.id == userId }
                                ?: emptyList()
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