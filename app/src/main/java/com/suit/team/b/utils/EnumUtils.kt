package com.suit.team.b.utils

enum class PlayerType {
    CPU,
    P1,
    P2,
}

enum class GameType {
    Multiplayer,
    Singleplayer
}

enum class Winner(private var winner: String) {
    Player("Player Win"),
    Opponent("Opponent Win");

    fun getWinner(): String {
        return this.winner
    }
}
