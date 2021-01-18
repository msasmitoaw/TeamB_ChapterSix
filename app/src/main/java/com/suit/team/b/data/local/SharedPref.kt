package com.suit.team.b.data.local

import android.content.Context
import android.util.Log
import com.suit.team.b.App
import com.suit.team.b.R
import com.suit.team.b.data.model.Score
import com.suit.team.b.utils.GameType
import com.suit.team.b.utils.PlayerType

object SharedPref {

    private const val KEY_SCORE_USER_VSP2 = "KEY_SCORE_USER_VSP2"
    private const val KEY_SCORE_USER_VSCPU = "KEY_SCORE_USER_VSCPU"
    private const val KEY_SCORE_P2 = "KEY_SCORE_P2"
    private const val KEY_SCORE_CPU = "KEY_SCORE_CPU"
    private const val KEY_USERNAME = "KEY_USERNAME"
    private const val KEY_ISLOGIN = "KEY_ISLOGIN"
    private const val KEY_ID = "KEY_ID"

    private val pref =
            App.weakReferenceContext.get()?.getSharedPreferences("pref", Context.MODE_PRIVATE)

    var username: String?
        get() = pref?.getString(KEY_USERNAME, "")
        set(username) {
            username?.let {
                pref?.edit()
                        ?.putString(KEY_ID, it)
                        ?.apply()
            }
        }

    var isLogin: Boolean?
        get() = pref?.getBoolean(KEY_ISLOGIN, false)
        set(value) {
            value?.let {
                pref?.edit()
                        ?.putBoolean(KEY_ISLOGIN, it)
                        ?.apply()
            }
        }
    var id: Int?
        get() = pref?.getInt(KEY_ID, 0)
        set(value) {
            value?.let {
                pref?.edit()
                        ?.putInt(KEY_ID, it)
                        ?.apply()
            }
        }

    fun scoreToPref(playerType: PlayerType, gameType: GameType) {
        when (playerType) {
            PlayerType.P1 -> {
                if (gameType == GameType.VSP) {
                    val getScoreUserVsP2 = pref?.getInt(KEY_SCORE_USER_VSP2, 0)
                    val score = getScoreUserVsP2?.plus(1)
                    pref?.edit()
                            ?.putInt(KEY_SCORE_USER_VSP2, score!!)
                            ?.apply()
                    Log.d("SharedPref", score.toString())
                } else {
                    val getScoreUserVsCpu = pref?.getInt(KEY_SCORE_USER_VSCPU, 0)
                    val score = getScoreUserVsCpu!!.plus(1)
                    pref?.edit()
                            ?.putInt(KEY_SCORE_USER_VSCPU, score)
                            ?.apply()
                    Log.d("SharedPref", score.toString())
                }
            }
            PlayerType.P2 -> {
                val getScoreP2 = pref?.getInt(KEY_SCORE_P2, 0)
                val score = getScoreP2!!.plus(1)
                pref?.edit()
                        ?.putInt(KEY_SCORE_P2, score)
                        ?.apply()
                Log.d("SharedPref", score.toString())
            }
            PlayerType.CPU -> {
                val getScoreCpu = pref?.getInt(KEY_SCORE_CPU, 0)
                val score = getScoreCpu!!.plus(1)
                pref?.edit()
                        ?.putInt(KEY_SCORE_CPU, score)
                        ?.apply()
                Log.d("SharedPref", score.toString())
            }
        }
    }


    fun getRankedScoreVsP(): MutableList<Score> {
        val nameP1 = username
        val scoreValueP1 = pref?.getInt(KEY_SCORE_USER_VSP2, 0)
        val nameP2 = App.weakReferenceContext.get()?.getString(R.string.player2)
        val scoreValueP2 = pref?.getInt(KEY_SCORE_P2, 0)
        val scoreRank = mutableListOf(
                Score(name = nameP1, scoreValue = scoreValueP1),
                Score(name = nameP2, scoreValue = scoreValueP2)
        )
        scoreRank.sortByDescending { it.scoreValue }
        return scoreRank
    }

    fun getRankedScoreVsCPU(): MutableList<Score> {
        val nameP1 = username
        val scoreValueP1 = pref?.getInt(KEY_SCORE_USER_VSCPU, 0)
        val nameCPU = App.weakReferenceContext.get()?.getString(R.string.CPU)
        val scoreValueCPU = pref?.getInt(KEY_SCORE_CPU, 0)
        val scoreRank = mutableListOf(
                Score(name = nameP1, scoreValue = scoreValueP1),
                Score(name = nameCPU, scoreValue = scoreValueCPU)
        )
        scoreRank.sortByDescending { it.scoreValue }
        return scoreRank
    }

    fun logout() {
        pref?.all?.clear()
    }
}
