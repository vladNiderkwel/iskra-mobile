package com.niderkwel.iskra_mobile.components.screens.post.details

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

class PostDetailsViewModelFactory(private val id: Int) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = PostDetailsViewModel(id) as T
}

class PostDetailsViewModel(private val id: Int) : ViewModel() {
    private val _state = MutableStateFlow(PostDetailsState())
    val state = _state.asStateFlow()

    private var job: Job? = null

    init {
        fetchPost()
    }

    fun fetchPost() {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(uiStatus = UiStatus.Loading)

            kotlin.runCatching { API.postService.get(id) }
                .onFailure {
                    _state.value = _state.value.copy(
                        uiStatus = UiStatus.Error("Что-то пошло не так!")
                    )
                }
                .onSuccess { response ->
                    if (response.isSuccessful) {
                        kotlin.runCatching { API.postService.increaseViews(id) }
                            .onFailure {  }
                        _state.value = _state.value.copy(
                            uiStatus = UiStatus.Success,
                            post = response.body()
                        )
                    } else {
                        _state.value = _state.value.copy(
                            uiStatus = UiStatus.Error("Что-то пошло не так")
                        )
                    }
                }
        }
    }
}