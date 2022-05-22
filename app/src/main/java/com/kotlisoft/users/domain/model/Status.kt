package com.kotlisoft.users.domain.model

import com.google.gson.annotations.SerializedName

enum class Status {
    @SerializedName("active")
    ACTIVE,
    @SerializedName("inactive")
    INACTIVE
}