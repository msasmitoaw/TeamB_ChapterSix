package com.rockpaperscissors.team.b.data.model

import com.rockpaperscissors.team.b.utils.GameType
import com.rockpaperscissors.team.b.utils.PlayerType

data class Score(
    val name: String? = null,
    val playerType: PlayerType? = null,
    val scoreValue: Int? = null,
    val gameType: GameType? = null
)
