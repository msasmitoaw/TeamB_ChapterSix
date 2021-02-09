package com.suit.team.b.data.model

import com.suit.team.b.utils.PlayerType

data class Battle(
        val name: String? = null,
        val result: String? = null,
        val mode: String? = null,
        val message: String? = null,
        val playerType: PlayerType? = null,
)