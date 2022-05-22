package com.kotlisoft.users.data.remote

import com.google.gson.annotations.SerializedName

data class PaginatedUsersResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("data")
    val users: List<UserResponse>,
)

data class Meta(
    @SerializedName("pagination")
    val pagination: Pagination
)

data class Pagination(
    @SerializedName("total")
    val total: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("limit")
    val limit: Int
)