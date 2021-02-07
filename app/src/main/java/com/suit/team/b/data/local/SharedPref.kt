package com.suit.team.b.data.local

import android.content.Context
import android.util.Log
import com.suit.team.b.App
import com.suit.team.b.utils.GameType
import com.suit.team.b.utils.PlayerType

object SharedPref {

    private const val KEY_SCORE_USER_VSP2 = "KEY_SCORE_USER_VSP2"
    private const val KEY_SCORE_USER_VS_CPU = "KEY_SCORE_USER_VS_CPU"
    private const val KEY_SCORE_P2 = "KEY_SCORE_P2"
    private const val KEY_SCORE_CPU = "KEY_SCORE_CPU"
    private const val KEY_NAME = "KEY_NAME"
    private const val KEY_IS_LOGIN = "KEY_IS_LOGIN"
    private const val KEY_ID = "KEY_ID"
    private const val KEY_USERNAME = "KEY_USERNAME"
    private const val KEY_TOKEN = "KEY_TOKEN"

    private val pref =
        App.weakReferenceContext.get()?.getSharedPreferences("suitPref", Context.MODE_PRIVATE)

    var token: String?
        get() = pref?.getString(KEY_TOKEN, "")
        set(value) {
            value?.let {
                pref?.edit()
                    ?.putString(KEY_TOKEN, it)
                    ?.apply()
            }
        }

    var isLogin: Boolean?
        get() = pref?.getBoolean(KEY_IS_LOGIN, false)
        set(value) {
            value?.let {
                pref?.edit()
                    ?.putBoolean(KEY_IS_LOGIN, it)
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

    var name: String?
        get() = pref?.getString(KEY_NAME, "")
        set(name) {
            name?.let {
                pref?.edit()
                    ?.putString(KEY_NAME, it)
                    ?.apply()
            }
        }

    var username: String?
        get() = pref?.getString(KEY_USERNAME, "")
        set(username) {
            username?.let {
                pref?.edit()
                    ?.putString(KEY_USERNAME, it)
                    ?.apply()
            }
        }

    fun logout() {
        pref?.edit()?.clear()?.apply()
    }
}
