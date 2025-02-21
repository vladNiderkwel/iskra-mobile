package com.niderkwel.iskra_mobile.components.screens.post.details

import com.niderkwel.domain.models.Post
import com.niderkwel.iskra_mobile.components.UiStatus

data class PostDetailsState(
    val uiStatus: UiStatus = UiStatus.Success,
    val post: Post? = null
)