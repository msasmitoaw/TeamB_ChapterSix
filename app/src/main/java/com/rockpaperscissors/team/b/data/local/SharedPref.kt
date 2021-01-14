package com.rockpaperscissors.team.b.data.local

import android.content.Context
import com.rockpaperscissors.team.b.App
import com.rockpaperscissors.team.b.R
import com.rockpaperscissors.team.b.data.model.Score
import com.rockpaperscissors.team.b.data.model.User
import com.rockpaperscissors.team.b.utils.GameType
import com.rockpaperscissors.team.b.utils.PlayerType

object SharedPref {

    private const val KEY_SCORE_USER_VSP2 = "KEY_SCORE_USER_VSP2"
    private const val KEY_SCORE_USER_VSCPU = "KEY_SCORE_USER_VSCPU"
    private const val KEY_SCORE_P2 = "KEY_SCORE_P2"
    private const val KEY_SCORE_CPU = "KEY_SCORE_CPU "

    private const val KEY_ID = "KEY_ID"
    private const val KEY_USERNAME = "USERNAME"

    private val pref = App.context?.getSharedPreferences("pref", Context.MODE_PRIVATE)

    fun getUser(): User {
        val id = pref?.getInt(KEY_ID, 0) ?: 0
        val username = pref?.getString(KEY_USERNAME, "").toString()
        return User(id, username)
    }

    fun scoreToPref(score: Score) {
        when (score.playerType) {
            PlayerType.P1 -> {
                if (score.gameType == GameType.VSP) {
                    pref?.edit()
                        ?.putInt(KEY_SCORE_USER_VSP2, score.scoreValue ?: 0)
                        ?.apply()
                } else {
                    pref?.edit()
                        ?.putInt(KEY_SCORE_USER_VSCPU, score.scoreValue ?: 0)
                        ?.apply()
                }
            }
            PlayerType.P2 -> {
                pref?.edit()
                    ?.putInt(KEY_SCORE_P2, score.scoreValue ?: 0)
                    ?.apply()
            }
            PlayerType.CPU -> {
                pref?.edit()
                    ?.putInt(KEY_SCORE_CPU, score.scoreValue ?: 0)
                    ?.apply()
            }
        }
    }

    fun getDataScoreVsP(): MutableList<Score> {
        val nameP1 = pref?.getString(KEY_USERNAME, App.context?.getString(R.string.player1))
        val scoreValueP1 = pref?.getInt(KEY_SCORE_USER_VSP2, 0)
        val nameP2 = App.context?.getString(R.string.player2)
        val scoreValueP2 = pref?.getInt(KEY_SCORE_P2, 0)
        return mutableListOf(
            Score(name = nameP1, scoreValue = scoreValueP1),
            Score(name = nameP2, scoreValue = scoreValueP2)
        )
    }

    fun getDataScoreVsCPU(): MutableList<Score> {
        val nameP1 = pref?.getString(KEY_USERNAME, App.context?.getString(R.string.player1))
        val scoreValueP1 = pref?.getInt(KEY_SCORE_CPU, 0)
        val nameCPU = App.context?.getString(R.string.CPU)
        val scoreValueCPU = pref?.getInt(KEY_SCORE_CPU, 0)
        return mutableListOf(
            Score(name = nameP1, scoreValue = scoreValueP1),
            Score(name = nameCPU, scoreValue = scoreValueCPU)
        )
    }

    //FOR DUMMY
    fun getScore(playerType: PlayerType, gameType: GameType? = null): String {
        var score: String? = null
        when (playerType) {
            PlayerType.P1 -> {
                score = if (gameType == GameType.VSP) {
                    pref?.getInt(KEY_SCORE_USER_VSP2, 0).toString()
                } else {
                    pref?.getInt(KEY_SCORE_USER_VSCPU, 0).toString()
                }
            }
            PlayerType.P2 -> {
                score = pref?.getInt(KEY_SCORE_P2, 0).toString()
            }
            PlayerType.CPU -> {
                score = pref?.getInt(KEY_SCORE_CPU, 0).toString()
            }
        }
        return score
    }
}