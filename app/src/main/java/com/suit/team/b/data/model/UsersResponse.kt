package com.suit.team.b.data.model


import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("success")
    val success: Boolean?
) {
    data class Data(
        @SerializedName("email")
        val email: String,
        @SerializedName("_id")
        val id: String,
        @SerializedName("photo")
        val photo: String?,
        @SerializedName("username")
        val username: String
    )
}
