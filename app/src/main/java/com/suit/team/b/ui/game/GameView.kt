package com.suit.team.b.ui.game

interface GameView {
    fun onPlayerOnePick()
    fun onPlayerTwoPick()
    fun onResult(result: String)
    fun onRefresh()
}
