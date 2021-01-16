package com.suit.team.b.ui.game

interface GameView {
    fun onPlayerOnePick()
    fun onPlayerTwoPick()
    fun onComputerPick()
    fun onResult(result: String)
    fun onRefresh()
}
