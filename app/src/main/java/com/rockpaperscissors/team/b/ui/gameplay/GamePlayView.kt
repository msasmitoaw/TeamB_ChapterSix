package com.rockpaperscissors.team.b.ui.gameplay

interface GamePlayView {
    fun onPlayerOnePick()
    fun onPlayerTwoPick()
    fun onComputerPick()
    fun onResult(result: String)
    fun onRefresh()
}
