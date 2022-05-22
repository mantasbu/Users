package com.kotlisoft.users.domain.model

import com.google.gson.annotations.SerializedName

enum class Gender {
    @SerializedName("male")
    MALE,
    @SerializedName("female")
    FEMALE
}