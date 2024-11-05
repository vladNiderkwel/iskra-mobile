package com.niderkwel.iskra_mobile.components.screens.questions.create

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.niderkwel.domain.API
import com.niderkwel.domain.models.Question
import com.niderkwel.iskra_mobile.components.UiStatus
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateQuestionViewModelFactory(private val sharedPreferences: SharedPreferences) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CreateQuestionViewModel(sharedPreferences) as T
}

class CreateQuestionViewModel(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _state = MutableStateFlow(CreateQuestionState())
    val state = _state.asStateFlow()

    private var job: Job? = null

    fun onQuestionChanged(value: String) {
        _state.value = _state.value.copy(question = value)
    }

    fun onSave(navController: NavController) {

        if(_state.value.question.isBlank()) return

        val id = sharedPreferences.getInt("userId", -1)

        if (id == -1) return

        job?.cancel()
        job = viewModelScope.launch {
            _state.value = _state.value.copy(uiStatus = UiStatus.Loading)

            val user = API.userService.get(id).body()!!

            kotlin.runCatching {
                API.questionService.create(
                    Question(
                        question = _state.value.question.trim(),
                        author = user,
                        phase = 0,
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
                        _state.value = _state.value.copy(
                            uiStatus = UiStatus.Success
                        )
                        navController.popBackStack()
                        navController.navigate("questions")
                    } else {
                        _state.value = _state.value.copy(
                            uiStatus = UiStatus.Error("Что-то пошло не так")
                        )
                    }
                }

        }
    }
}