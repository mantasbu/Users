package com.kotlisoft.users.data.remote

import com.google.gson.annotations.SerializedName
import com.kotlisoft.users.domain.model.Gender
import com.kotlisoft.users.domain.model.Status

data class CreateUserRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: Gender,
    @SerializedName("status")
    val status: Status
)