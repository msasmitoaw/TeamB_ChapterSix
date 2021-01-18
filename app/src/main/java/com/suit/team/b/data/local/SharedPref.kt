package com.suit.team.b.data.local

import android.content.Context
import com.suit.team.b.App
import com.suit.team.b.R
import com.suit.team.b.data.model.Score

object SharedPref {

    private const val KEY_SCORE_USER_VSP2 = "KEY_SCORE_USER_VSP2"
    private const val KEY_SCORE_USER_VSCPU = "KEY_SCORE_USER_VSCPU"
    private const val KEY_SCORE_P2 = "KEY_SCORE_P2"
    private const val KEY_SCORE_CPU = "KEY_SCORE_CPU"
    private const val KEY_ID = "KEY_ID"
    private const val KEY_USERNAME = "USERNAME"

    private val pref = App.context?.getSharedPreferences("pref", Context.MODE_PRIVATE)

    var username: String?
        get() = pref?.getString(KEY_USERNAME,"")
        set(username) {
            username?.let {
                pref?.edit()
                    ?.putString(KEY_ID, it)
                    ?.apply()
            }
        }

    var id: Int?
        get() = pref?.getInt(KEY_ID,0)
        set(value) {
            value?.let {
                pref?.edit()
                    ?.putInt(KEY_ID, it)
                    ?.apply()
            }
        }

    fun getRankedScoreVsP(): MutableList<Score> {
        val nameP1 = pref?.getString(KEY_USERNAME, App.context?.getString(R.string.player1))
        val scoreValueP1 = pref?.getInt(KEY_SCORE_USER_VSP2, 0)
        val nameP2 = App.context?.getString(R.string.player2)
        val scoreValueP2 = pref?.getInt(KEY_SCORE_P2, 0)
        val scoreRank = mutableListOf(
            Score(name = nameP1, scoreValue = scoreValueP1),
            Score(name = nameP2, scoreValue = scoreValueP2)
        )
        scoreRank.sortByDescending { it.scoreValue }
        return scoreRank
    }

    fun getRankedScoreVsCPU(): MutableList<Score> {
        val nameP1 = pref?.getString(KEY_USERNAME, App.context?.getString(R.string.player1))
        val scoreValueP1 = pref?.getInt(KEY_SCORE_USER_VSCPU, 0)
        val nameCPU = App.context?.getString(R.string.CPU)
        val scoreValueCPU = pref?.getInt(KEY_SCORE_CPU, 0)
        val scoreRank = mutableListOf(
            Score(name = nameP1, scoreValue = scoreValueP1),
            Score(name = nameCPU, scoreValue = scoreValueCPU)
        )
        scoreRank.sortByDescending { it.scoreValue }
        return scoreRank
    }

    fun getLoginUserId(): Int? {
        return pref?.getInt(KEY_ID, 0)
    }

    fun logout() {
        pref?.all?.clear()
    }
}

