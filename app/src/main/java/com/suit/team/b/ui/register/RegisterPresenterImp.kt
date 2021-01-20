package com.suit.team.b.ui.register

import android.content.Context
import android.util.Patterns
import com.suit.team.b.App
import com.suit.team.b.R
import com.suit.team.b.data.db.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterPresenterImp(private val view: RegisterView) : RegisterPresenter {
    override fun register(username: String, password: String, name: String, email: String) {
        val context = view as Context
        mutableListOf(
            username, password, name, email
        ).forEachIndexed { index, i ->
            when {
                i.isEmpty() -> {
                    when (index) {
                        0 -> view.onError(context.getString(R.string.username_validation))
                        1 -> view.onError(context.getString(R.string.password_validation))
                        2 -> view.onError(context.getString(R.string.name_validation))
                        3 -> view.onError(context.getString(R.string.email_validation))
                    }
                    return
                }
                index == 3 -> {
                    if (!Patterns.EMAIL_ADDRESS.matcher(i).matches()) {
                        view.onError(context.getString(R.string.email_format_validation))
                        return
                    }
                }
            }
        }

        val newUser =
            UserEntity(username = username, password = password, name = name, email = email)
        val appDb = App.appDb

        GlobalScope.launch(Dispatchers.IO) {
            val userEntity = appDb
                ?.dataUser()?.fetchUserByName(username)
            if (username == userEntity?.name) {
                launch(Dispatchers.Main) {
                    view.onError(context.getString(R.string.username_exist))
                }
            } else {
                appDb?.dataUser()
                    ?.insertUser(newUser)
                launch(Dispatchers.Main) {
                    view.onSuccess(context.getString(R.string.sign_up_success))
                }
            }
        }
    }

}
