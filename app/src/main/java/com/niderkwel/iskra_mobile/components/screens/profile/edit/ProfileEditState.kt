package com.niderkwel.iskra_mobile.components.screens.profile.edit

import android.net.Uri
import com.niderkwel.domain.models.User
import com.niderkwel.iskra_mobile.components.UiStatus
import java.nio.file.Path

data class ProfileEditState(
    val uiStatus: UiStatus = UiStatus.Success,
    val user: User? = null,
    val image: Uri? = null,
    val file: Path? = null
)