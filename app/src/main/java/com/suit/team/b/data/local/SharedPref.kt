package com.suit.team.b.data.local

import android.content.Context
import com.suit.team.b.App

object SharedPref {

    private const val KEY_IS_LOGIN = "KEY_IS_LOGIN"
    private const val KEY_ID = "KEY_ID"
    private const val KEY_USERNAME = "KEY_USERNAME"
    private const val KEY_PASSWORD = "KEY_PASSWORD"
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

    var username: String?
        get() = pref?.getString(KEY_USERNAME, "")
        set(username) {
            username?.let {
                pref?.edit()
                    ?.putString(KEY_USERNAME, it)
                    ?.apply()
            }
        }

    var password: String?
        get() = pref?.getString(KEY_PASSWORD, "")
        set(value) {
            value?.let {
                pref?.edit()
                    ?.putString(KEY_PASSWORD, it)
                    ?.apply()
            }
        }

    fun logout() {
        pref?.edit()?.clear()?.apply()
    }
}
