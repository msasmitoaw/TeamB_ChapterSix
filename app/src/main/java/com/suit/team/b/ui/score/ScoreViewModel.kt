package com.suit.team.b.ui.score

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suit.team.b.App
import com.suit.team.b.R
import com.suit.team.b.data.local.SharedPref
import com.suit.team.b.data.model.BattleResponse
import com.suit.team.b.data.model.Score
import com.suit.team.b.data.remote.ApiModule
import com.suit.team.b.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class ScoreViewModel : ViewModel() {

    private var compositeDis: CompositeDisposable? = null
    val scoreMulti = MutableLiveData<MutableList<Score>>()
    val scoreSingle = MutableLiveData<MutableList<Score>>()
    val errorRegister: MutableLiveData<String>? = null

    val battleResult = MutableLiveData<MutableList<BattleResponse.Data>>()

    override fun onCleared() {
        super.onCleared()
        compositeDis?.dispose()
    }

    fun fetchScoreMulti() {
        val disposable = ApiModule.service.getBattle("Bearer " + SharedPref.token)
            .map { br -> toScore(br.data) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    scoreMulti.value =
                        it.filter { it1 -> it1.gameType == GameType.Multiplayer }
                            .sortedByDescending { it2 -> it2.scoreValue }
                            .toMutableList()
                }, {
                    errorRegister?.value =
                        getErrorMessage(it.getServiceErrorMsg(), it.getErrorThrowableCode())
                    it.printStackTrace()
                }
            )
        compositeDis?.add(disposable)
    }

    fun fetchScoreSingle() {
        val disposable = ApiModule.service.getBattle("Bearer " + SharedPref.token)
            .map { br -> toScore(br.data) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    scoreSingle.value =
                        it.filter { it1 -> it1.gameType == GameType.Singleplayer }
                            .sortedByDescending { it2 -> it2.scoreValue }
                            .toMutableList()
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

    fun fetchResult() {
        val disposable = ApiModule.service.getBattle("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MDFmYzBlOTcyN2E4YzAwMTc4Zjk2MmQiLCJ1c2VybmFtZSI6InNhbG1hbiIsImVtYWlsIjoic2FsbWFuQHlhaG9vLmNvbSIsImlhdCI6MTYxMjg0ODc2NywiZXhwIjoxNjEyODU1OTY3fQ.SLzcFGEBtgwiU7VArd3RwLIu0Z07Fi1U4NWj3pLtwHQ")
            .map { br -> br.data }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    battleResult.value = it.toMutableList()
                }, {
                    errorRegister?.value =
                        getErrorMessage(it.getServiceErrorMsg(), it.getErrorThrowableCode())
                    it.printStackTrace()
                })
        compositeDis?.add(disposable)
    }
}
