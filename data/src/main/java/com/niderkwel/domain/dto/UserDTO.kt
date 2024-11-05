package com.niderkwel.domain.dto

import java.io.File

data class UserDTO(
    var name: String,
    var email: String,
    var password: String,
    var photo: File? = null
)