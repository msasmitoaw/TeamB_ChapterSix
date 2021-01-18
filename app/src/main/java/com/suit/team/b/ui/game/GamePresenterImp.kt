package com.suit.team.b.ui.game

import android.content.Context
import com.suit.team.b.R
import com.suit.team.b.data.local.SharedPref
import com.suit.team.b.data.model.Player
import com.suit.team.b.utils.GameType
import com.suit.team.b.utils.PlayerType
import com.suit.team.b.utils.string

class GamePresenterImp(
        private val view: GameView
) : GamePresenter {
    private lateinit var player: Player
    private lateinit var playerTwo: Player
    private val context = view as Context
    private var weakness =
            mapOf(
                    context.string(R.string.scissors_caps) to context.string(R.string.rock_caps),
                    context.string(R.string.paper_caps) to context.string(R.string.scissors_caps),
                    context.string(R.string.rock_caps) to context.string(R.string.paper_caps)
            )

    override fun getPlayerTwo(): Player {
        return this.playerTwo
    }

    override fun getPlayerOneName(): String {
        return SharedPref.getUsername().toString()
    }

    override fun setPlayer(player: Player) {
        this.player = player
    }

    override fun setPlayerTwo() {
        this.playerTwo = Player(weakness.entries.random().key)
    }

    override fun setPlayerTwo(player: Player) {
        this.playerTwo = player
    }

    override fun result(mode: String) {
        var playerType = PlayerType.P2
        val gameType = when (mode) {
            "CPU" -> {
                playerType = PlayerType.CPU; GameType.VSCPU
            }
            else -> GameType.VSP
        }
        view.onResult(
                when (player.bet) {
                    playerTwo.bet -> context.string(R.string.draw)
                    weakness[playerTwo.bet] -> {
                        SharedPref.scoreToPref(PlayerType.P1, gameType)
                        context.string(R.string.player_one_win)
                    }
                    else -> {
                        SharedPref.scoreToPref(playerType, gameType)
                        context.string(R.string.player_two_win)
                    }
                }
        )
    }
}
