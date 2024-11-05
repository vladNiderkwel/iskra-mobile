package com.niderkwel.iskra_mobile.components.screens.task.details

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.niderkwel.domain.API
import com.niderkwel.domain.models.Option
import com.niderkwel.domain.models.UserAnswer
import com.niderkwel.domain.models.UserTask
import com.niderkwel.iskra_mobile.components.Screen
import com.niderkwel.iskra_mobile.components.UiStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class TaskDetailsViewModelFactory(
    private val id: Int,
    private val sharedPreferences: SharedPreferences
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        TaskDetailsViewModel(id, sharedPreferences) as T
}

class TaskDetailsViewModel(
    private val id: Int,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(TaskDetailsState())
    val state = _state.asStateFlow()

    private var job: Job? = null

    init {
        fetchDetails()
    }

    fun fetchDetails() {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(uiStatus = UiStatus.Loading)

            val response = API.taskService.get(id)
            if (response.isSuccessful) {
                val task = response.body()
                _state.value = _state.value.copy(
                    uiStatus = UiStatus.Success,
                    task = task,
                    userTask = UserTask(
                        id = -1,
                        task = task!!,
                        user = API.userService
                            .get(sharedPreferences.getInt("userId", -1))
                            .body()!!,
                        answers = task.subtasks.map { subtask ->
                            val randomId = Random.nextInt(0, 10000)
                            UserAnswer(
                                id = randomId,
                                subtask = subtask,
                                answers = emptyList(),
                                writtenAnswer = ""
                            )
                        }.toList()
                    )
                )
            } else
                _state.value =
                    _state.value.copy(uiStatus = UiStatus.Error("Что-то пошло не так"))
        }
    }

    fun onWrittenSubtaskUpdate(id: Int, value: String) {
        val userAnswerId = _state.value.userTask!!.answers.indexOfFirst { it.id == id }

        val list = _state.value.userTask!!.answers.toMutableList()

        list[userAnswerId] = list[userAnswerId].copy(
            writtenAnswer = value
        )

        _state.value = _state.value.copy(
            userTask = _state.value.userTask!!.copy(
                answers = list
            )
        )
    }

    fun onCheckboxSubtaskUpdate(id: Int, value: Option, bool: Boolean) {
        val userAnswerId = _state.value.userTask!!.answers.indexOfFirst { it.id == id }
        val list = _state.value.userTask!!.answers.toMutableList()

        var answers = list[userAnswerId].answers.toMutableList()

        if (bool) answers.add(value)
        else answers = answers.filter { it.id != value.id }.toMutableList()

        list[userAnswerId] = list[userAnswerId].copy(
            answers = answers
        )

        _state.value = _state.value.copy(
            userTask = _state.value.userTask!!.copy(
                answers = list
            )
        )
    }

    fun onRadiobuttonSubtaskUpdate(id: Int, value: Option) {
        val userAnswerId = _state.value.userTask!!.answers.indexOfFirst { it.id == id }

        val list = _state.value.userTask!!.answers.toMutableList()

        list[userAnswerId] = list[userAnswerId].copy(
            answers = listOf(value)
        )

        _state.value = _state.value.copy(
            userTask = _state.value.userTask!!.copy(
                answers = list
            )
        )
    }

    fun onSave(navController: NavController) {
        job?.cancel()
        job = viewModelScope.launch {
            _state.value = _state.value.copy(saving = UiStatus.Loading)

            kotlin.runCatching {
                API.userTaskService.create(
                    _state.value.userTask!!
                )
            }
                .onFailure {
                    _state.value = _state.value.copy(
                        saving = UiStatus.Error("Что-то пошло не так")
                    )
                }
                .onSuccess {
                    if (it.isSuccessful) {
                        _state.value = _state.value.copy(
                            saving = UiStatus.Success
                        )
                        navController.popBackStack()
                        navController.navigate(Screen.Profile.path)
                    }
                }
        }
    }
}