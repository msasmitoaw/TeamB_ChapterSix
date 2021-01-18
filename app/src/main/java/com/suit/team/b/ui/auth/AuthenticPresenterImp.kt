package com.suit.team.b.ui.auth

import com.suit.team.b.App
import com.suit.team.b.data.local.SharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AuthenticPresenterImp(private val view: AuthenticView) : AuthenticPresenter {
    override fun login(username: String, password: String) {
        val appDb = App.appDb

        GlobalScope.launch(Dispatchers.IO)
        {
            val userEntity = appDb
                ?.dataUser()?.checkUserPassword(username,password)
            //launch ini memindahkan ke UI agar bisa ditampillkan, karena launch yang pertama masih proses background (tidak punya UI)
            launch(Dispatchers.Main){
                if ( (username == userEntity?.name)  && (password == userEntity?.password)  ) {
                    SharedPref.isLogin = true
                    SharedPref.id = userEntity.id
                    view.onSuccess(userEntity.id)
                } else {
                    view.onError("Username atau password kamuuhh salah!!")
                }
            }
        }
    }

    override fun checkIsLogin(): Boolean = SharedPref.isLogin == true
    override fun getId(): Int? =SharedPref.id

}