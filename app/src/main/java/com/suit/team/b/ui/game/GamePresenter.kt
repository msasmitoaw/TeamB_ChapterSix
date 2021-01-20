package com.suit.team.b.ui.game

import com.suit.team.b.data.model.Player

interface GamePresenter {
    fun getPlayerOneName(): String
    fun setPlayer(player: Player)
    fun setPlayerTwo()
    fun getPlayerTwo(): Player
    fun setPlayerTwo(player: Player)
    fun result(mode: String)
}
