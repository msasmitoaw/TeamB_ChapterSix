package com.suit.team.b.data.model


import com.google.gson.annotations.SerializedName

data class BattleResponse(
    @SerializedName("data")
    var `data`: List<Data>,
    @SerializedName("success")
    var success: Boolean
) {
    data class Data(
        @SerializedName("createdAt")
        var createdAt: String,
        @SerializedName("_id")
        var id: String,
        @SerializedName("message")
        var message: String,
        @SerializedName("mode")
        var mode: String,
        @SerializedName("result")
        var result: String,
        @SerializedName("updatedAt")
        var updatedAt: String
    )
}
