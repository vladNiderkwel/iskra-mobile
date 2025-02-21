package com.niderkwel.iskra_mobile.components.screens.profile.edit

import android.content.SharedPreferences
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.niderkwel.domain.API
import com.niderkwel.iskra_mobile.components.Screen
import com.niderkwel.iskra_mobile.components.UiStatus
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.nio.file.Path
import kotlin.io.path.deleteIfExists

class ProfileEditViewModelFactory(private val sharedPreferences: SharedPreferences) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ProfileEditViewModel(sharedPreferences) as T
}

class ProfileEditViewModel(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileEditState())
    val state = _state.asStateFlow()

    private var job: Job? = null

    init {
        fetchUserInfo()
    }

    private fun createBody(): MultipartBody {
        val user = _state.value.user!!

        val body = MultipartBody
            .Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("id", user.id.toString())
            .addFormDataPart("name", user.name)
            .addFormDataPart("email", user.email)
            .addFormDataPart("password", user.password)
            .addFormDataPart("photoUrl", user.photoUrl)
            .addFormDataPart("isBlocked", user.isBlocked.toString())

        _state.value.file?.let {
            val file = it.toFile()
            body.addFormDataPart(
                "photo",
                file.name,
                file.asRequestBody("image/*".toMediaTypeOrNull())
            )
        }
        return body.build()
    }

    fun onSave(navController: NavController) {
        job?.cancel()
        job = viewModelScope.launch {
            _state.value = _state.value.copy(uiStatus = UiStatus.Loading)

            kotlin.runCatching {
                API.userService.update(createBody())
            }
                .onFailure {
                    _state.value = _state.value.copy(
                        uiStatus = UiStatus.Error("Что-то пошло не так")
                    )
                }
                .onSuccess {
                    _state.value = _state.value.copy(
                        uiStatus = UiStatus.Success
                    )
                    _state.value.file?.deleteIfExists()
                    navController.popBackStack()
                    navController.navigate(Screen.Profile.path)
                }

        }
    }

    fun onNameUpdate(value: String) {
        val user = _state.value.user!!
        _state.value = _state.value.copy(user = user.copy(name = value))
    }

    fun onEmailUpdate(value: String) {
        val user = _state.value.user!!
        _state.value = _state.value.copy(user = user.copy(email = value))
    }

    fun onImageUpdate(file: Path, value: Uri) {
        _state.value = _state.value.copy(
            image = value,
            file = file
        )
    }

    fun fetchUserInfo() {
        job?.cancel()
        job = viewModelScope.launch {
            _state.value = _state.value.copy(uiStatus = UiStatus.Loading)

            val id = getUserId()

            kotlin.runCatching { API.userService.get(id) }
                .onSuccess { response ->
                    if (response.isSuccessful) {
                        _state.value = _state.value.copy(
                            uiStatus = UiStatus.Success,
                            user = response.body()
                        )
                    }
                }
                .onFailure {
                    _state.value = _state.value.copy(
                        uiStatus = UiStatus.Error("Что-то пошлоп не так")
                    )
                }
        }
    }

    private fun getUserId(): Int =
        sharedPreferences.getInt("userId", 0)
}