package com.suit.team.b.utils

enum class PlayerType {
    P1,
    P2,
    CPU
}

enum class GameType {
    VSP,
    VSCPU,
    Multiplayer,
    Singleplayer
}

enum class ScoreType{
    HISTORY,
    BOOKMARK
}

enum class Winner(private var winner: String) {
    Player("Player Win"),
    Opponent("Opponent Win"),
    Draw("Draw");

    fun getWinner(): String {
        return this.winner
    }
}
