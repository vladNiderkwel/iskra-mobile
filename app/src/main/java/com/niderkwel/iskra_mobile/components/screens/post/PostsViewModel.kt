package com.niderkwel.iskra_mobile.components.screens.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niderkwel.domain.API
import com.niderkwel.iskra_mobile.components.UiStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostsViewModel : ViewModel() {
    private val _state = MutableStateFlow(PostsState())
    val state = _state.asStateFlow()

    private var job: Job? = null

    init {
        fetchPosts()
    }

    fun fetchPosts(page: Int = 1) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(uiStatus = UiStatus.Loading)

            val response = API.postService.all()
            if (response.isSuccessful) {
                _state.value = _state.value.copy(
                    uiStatus = UiStatus.Success,
                    posts = if (response.body() == null) emptyList()
                    else response.body()!!.content
                )
            } else
                _state.value =
                    _state.value.copy(uiStatus = UiStatus.Error("Что-то пошло не так"))

        }
    }
}