package com.rockpaperscissors.team.b.ui.gameplay

interface GamePlayView {
    fun onResult(result: String)
    fun onRefresh()
    fun onPlayerPick()
    fun onComputerPick()
    fun onPlayRound()
}
