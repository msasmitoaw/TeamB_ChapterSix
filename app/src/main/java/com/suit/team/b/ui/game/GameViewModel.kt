package com.suit.team.b.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.suit.team.b.R
import com.suit.team.b.data.local.SharedPref
import com.suit.team.b.data.model.BattleRequest
import com.suit.team.b.data.model.Player
import com.suit.team.b.data.remote.ApiService
import com.suit.team.b.utils.GameType
import com.suit.team.b.utils.getServiceErrorMsg
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

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
    private val errorResponse = MutableLiveData<String>()

    fun onErrorResponse(): LiveData<String> = errorResponse

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

    fun result(mode: GameType): Int {
        val gameOver = when (player.bet) {
            playerTwo.bet -> R.string.draw
            weakness[playerTwo.bet] -> R.string.player_one_win
            else -> R.string.player_two_win
        }
        val battleResult = when (gameOver) {
            R.string.draw -> "Draw"
            R.string.player_one_win -> "Player Win"
            else -> "Opponent Win"
        }
        battle(BattleRequest(mode = mode.name, result = battleResult))
        return gameOver
    }

    private fun battle(battleRequest: BattleRequest) {
        disposable = service.battle("Bearer " + SharedPref.token, battleRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }) {
                errorResponse.value = it.getServiceErrorMsg()
                it.printStackTrace()
            }
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
