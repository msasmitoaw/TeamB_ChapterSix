package com.suit.team.b.data.model


import com.google.gson.annotations.SerializedName

data class MeResponse(
    @SerializedName("data")
    val data: Data,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("errors")
    val errors: String?
) {
    data class Data(
        @SerializedName("email")
        val email: String,
        @SerializedName("_id")
        val id: String,
        @SerializedName("username")
        val username: String
    )
}
