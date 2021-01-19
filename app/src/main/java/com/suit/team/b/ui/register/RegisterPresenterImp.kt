package com.suit.team.b.ui.register

import com.suit.team.b.App
import com.suit.team.b.data.db.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterPresenterImp(private val view: RegisterView) : RegisterPresenter {
    override fun register(username: String, password: String, name: String, email: String) {
        val newUser =
            UserEntity(username = username, password = password, name = name, email = email)
        val appDb = App.appDb

        GlobalScope.launch(Dispatchers.IO) {
            val userEntity = appDb
                ?.dataUser()?.fetchUserByName(username)
            if (username == userEntity?.name) {
                launch(Dispatchers.Main) {
                    view.onError("Username kamu sama!")
                }
            } else {
                appDb?.dataUser()
                    ?.insertUser(newUser)
                launch(Dispatchers.Main) {
                    view.onSuccess()
                }
            }
        }
    }

}
