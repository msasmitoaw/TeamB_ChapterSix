package com.suit.team.b.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.suit.team.b.R
import com.suit.team.b.data.model.Player
import com.suit.team.b.data.remote.ApiService
import io.reactivex.disposables.Disposable

class GameViewModel(private val service: ApiService) : ViewModel() {
    private var disposable: Disposable? = null
    private lateinit var player: Player
    private lateinit var playerTwo: Player
    private var weakness =
        mapOf(
            R.string.scissors_caps to R.string.rock_caps,
            R.string.paper_caps to R.string.scissors_caps,
            R.string.rock_caps to R.string.paper_caps
        )

    fun getPlayerTwo(): Player = this.playerTwo

    fun setPlayer(player: Player) {
        this.player = player
    }

    fun setPlayerTwo() {
        this.playerTwo = Player(weakness.entries.random().key)
    }

    fun setPlayerTwo(player: Player) {
        this.playerTwo = player
    }

    fun result(): Int = when (player.bet) {
        playerTwo.bet -> R.string.draw
        weakness[playerTwo.bet] -> R.string.player_one_win
        else -> R.string.player_two_win
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    class Factory(private val service: ApiService) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return GameViewModel(service) as T
        }
    }
}
