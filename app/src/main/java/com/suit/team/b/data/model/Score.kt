package com.suit.team.b.data.model

import com.suit.team.b.utils.GameType
import com.suit.team.b.utils.PlayerType

data class Score(
    val name: String? = null,
    val playerType: PlayerType? = null,
    val scoreValue: Int? = null,
    val gameType: GameType? = null
)
