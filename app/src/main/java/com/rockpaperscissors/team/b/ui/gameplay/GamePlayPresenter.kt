package com.rockpaperscissors.team.b.ui.gameplay

import com.rockpaperscissors.team.b.data.model.Player

interface GamePlayPresenter {
    fun setPlayer(player: Player)
    fun setPlayerTwo()
    fun setPlayerTwo(player: Player)
    fun result()
}
