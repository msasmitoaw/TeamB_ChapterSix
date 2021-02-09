package com.suit.team.b.ui.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.suit.team.b.App
import com.suit.team.b.R
import com.suit.team.b.data.local.SharedPref
import com.suit.team.b.data.model.Battle
import com.suit.team.b.data.model.BattleResponse
import com.suit.team.b.data.model.Score
import com.suit.team.b.data.remote.ApiModule
import com.suit.team.b.data.remote.ApiService
import com.suit.team.b.ui.auth.AuthViewModel
import com.suit.team.b.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class HistoryViewModel(private val service: ApiService, private val pref: SharedPref) : ViewModel() {
    private var compositeDis: CompositeDisposable? = null

    val resultScore = MutableLiveData<MutableList<Score>>()
    val errorRegister: MutableLiveData<String>? = null

    override fun onCleared() {
        super.onCleared()
        compositeDis?.dispose()
    }

    fun fetchScoreMulti() {
        val disposable = ApiModule.service.getBattle("Bearer " +SharedPref.token)
                .map { br -> toScore(br.data) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    resultScore.value = it.toMutableList()
                }, {
                    errorRegister?.value =
                        getErrorMessage(it.getServiceErrorMsg(), it.getErrorThrowableCode())
                    it.printStackTrace()
                })
        compositeDis?.add(disposable)
    }

    private fun toScore(data: List<BattleResponse.Data>): MutableList<Score> {

        val matchSingle =
                data.filter { it.mode == GameType.Singleplayer.toString() }
        val winSingleCount =
                matchSingle.filter { it.result == Winner.Player.getWinner() }
                        .count()
        val loseSingleCount =
                matchSingle.filter { it.result == Winner.Opponent.getWinner() }
                        .count()

        val matchMulti =
                data.filter { it.mode == GameType.Multiplayer.toString() }
        val winMultiCount =
                matchMulti.filter { it.result == Winner.Player.getWinner() }.count()
        val loseMultiCount =
                matchMulti.filter { it.result == Winner.Opponent.getWinner() }
                        .count()

        return mutableListOf(
                Score(
                        getFromToken(getString(R.string.username)?.toLowerCase(Locale.ROOT)),
                        PlayerType.P1,
                        winSingleCount,
                        GameType.Singleplayer,
                ),
                Score(
                        getString(R.string.CPU),
                        PlayerType.CPU,
                        loseSingleCount,
                        GameType.Singleplayer
                ),
                Score(
                        getFromToken(getString(R.string.username)?.toLowerCase(Locale.ROOT)),
                        PlayerType.P1,
                        winMultiCount,
                        GameType.Multiplayer,
                ),
                Score(
                        App.weakReferenceContext.get()?.getString(R.string.player2),
                        PlayerType.P2,
                        loseMultiCount,
                        GameType.Multiplayer
                )
        )
    }

    class Factory(private val service: ApiService, private val pref: SharedPref) :
            ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(service, pref) as T
        }
    }
}