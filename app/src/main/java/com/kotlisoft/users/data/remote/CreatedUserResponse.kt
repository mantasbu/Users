package com.kotlisoft.users.data.remote

import com.google.gson.annotations.SerializedName
import com.kotlisoft.users.domain.model.Gender
import com.kotlisoft.users.domain.model.Status

data class CreatedUserResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val data: UserResponse
)