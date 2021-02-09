package com.suit.team.b.data.model


import com.google.gson.annotations.SerializedName

data class BattleRequest(
    @SerializedName("mode")
    val mode: String,
    @SerializedName("result")
    val result: String
)
