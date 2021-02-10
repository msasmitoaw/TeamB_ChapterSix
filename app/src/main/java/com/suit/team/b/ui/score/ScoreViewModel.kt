package com.suit.team.b.ui.score

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suit.team.b.App
import com.suit.team.b.R
import com.suit.team.b.data.db.ResultEntity
import com.suit.team.b.data.local.SharedPref
import com.suit.team.b.data.model.BattleBookmark
import com.suit.team.b.data.model.BattleResponse
import com.suit.team.b.data.model.Score
import com.suit.team.b.data.remote.ApiModule
import com.suit.team.b.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*

class ScoreViewModel : ViewModel() {

    private val appDb = App.appDb
    private var compositeDis: CompositeDisposable? = null
    val scoreMulti = MutableLiveData<MutableList<Score>>()
    val scoreSingle = MutableLiveData<MutableList<Score>>()
    val errorResponse: MutableLiveData<String>? = null
    val battleHistory = MutableLiveData<MutableList<BattleResponse.Data>>()
    val battleBookmark = MutableLiveData<MutableList<BattleBookmark>>()
    var bmHistory = MutableLiveData<MutableList<BattleBookmark>>()


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
                    errorResponse?.value =
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
                    errorResponse?.value =
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

    fun fetchBattleHistory() {
        val disposable = ApiModule.service.getBattle("Bearer " + SharedPref.token)
            .map { br -> br.data }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    battleHistory.value = it.toMutableList()
                }, {
                    errorResponse?.value =
                        getErrorMessage(it.getServiceErrorMsg(), it.getErrorThrowableCode())
                    it.printStackTrace()
                })
        compositeDis?.add(disposable)
    }

    fun toBattleBm(data: List<BattleResponse.Data>) {
        GlobalScope.launch(Dispatchers.Main) {
            val ids = GlobalScope.async(Dispatchers.IO) { appDb?.bookmark()?.fetchId() }
            val booked =
                data.filter { ids.await()?.contains(it.id) == true }
                    .map() {
                        BattleBookmark(
                            createdAt = it.createdAt,
                            id = it.id,
                            mode = it.mode,
                            message = it.message,
                            result = it.result,
                            updatedAt = it.updatedAt,
                            booked = true
                        )
                    }.toMutableList()

            val unBooked =
                data.filter { ids.await()?.contains(it.id) == false }
                    .map() {
                        BattleBookmark(
                            createdAt = it.createdAt,
                            id = it.id,
                            mode = it.mode,
                            message = it.message,
                            result = it.result,
                            updatedAt = it.updatedAt,
                            booked = false
                        )
                    }.toMutableList()

            booked.addAll(unBooked)
            battleBookmark.value = booked
        }
    }

    fun insertBookmark(data: BattleBookmark) {
        val result = ResultEntity(
            createdAt = data.createdAt,
            id = data.id,
            mode = data.mode,
            message = data.message,
            result = data.result,
            updatedAt = data.updatedAt
        )

        GlobalScope.launch(Dispatchers.IO) {
            try {
                appDb?.bookmark()?.insertBookmark(result)
            } catch (e: Throwable) {
                launch(Dispatchers.Main) {
                    errorResponse?.value = getString(R.string.bookmark_save_err)
                    e.printStackTrace()
                }
            }
        }
    }

    fun deleteBookmark(id: String) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                appDb?.bookmark()?.delBookmark(id)
            } catch (e: Throwable) {
                launch(Dispatchers.Main) {
                    errorResponse?.value = getString(R.string.bookmark_del_err)
                    e.printStackTrace()
                }
            }
        }
    }

    fun fetchBookmark() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val bmData = appDb?.bookmark()?.fetchBookmark()?.map {
                    BattleBookmark(
                        createdAt = it.createdAt,
                        id = it.id,
                        mode = it.mode,
                        result = it.result,
                        updatedAt = it.updatedAt,
                        message = it.message,
                        booked = true
                    )
                }?.toMutableList()
                launch(Dispatchers.Main) {
                    bmHistory.value = bmData
                }
            } catch (e: Throwable) {
                launch(Dispatchers.Main) {
                    errorResponse?.value = getString(R.string.get_bookmark_err)
                    e.printStackTrace()
                }
            }
        }
    }
}
