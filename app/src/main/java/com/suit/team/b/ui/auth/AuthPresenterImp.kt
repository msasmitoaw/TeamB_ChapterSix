package com.suit.team.b.ui.auth

import android.content.Context
import com.suit.team.b.App
import com.suit.team.b.R
import com.suit.team.b.data.local.SharedPref
import com.suit.team.b.utils.string
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AuthPresenterImp(private val view: AuthView) : AuthPresenter {
    private val context = view as Context

    override fun login(username: String, password: String) {
        val appDb = App.appDb

        GlobalScope.launch(Dispatchers.IO)
        {
            val userEntity = appDb
                ?.dataUser()?.checkUserPassword(username, password)
            launch(Dispatchers.Main) {
                if ((username == userEntity?.username) && (password == userEntity.password)) {
                    SharedPref.isLogin = true
                    SharedPref.id = userEntity.id
                    SharedPref.name = userEntity.name
                    SharedPref.username = userEntity.username
                    view.onSuccess(userEntity.id)
                } else {
                    view.onError(
                        context.string(R.string.not_authenticated_msg)
                    )
                }
            }
        }
    }

    override fun checkIsLogin(): Boolean = SharedPref.isLogin == true
    override fun getId(): Int? = SharedPref.id

}
