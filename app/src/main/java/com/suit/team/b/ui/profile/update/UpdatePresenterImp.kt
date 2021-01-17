package com.suit.team.b.ui.profile.update

import android.content.Context
import com.suit.team.b.App
import com.suit.team.b.R
import com.suit.team.b.data.db.UserEntity
import com.suit.team.b.data.local.SharedPref
import com.suit.team.b.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdatePresenterImp(private val view: UpdateView) : UpdatePresenter {

    override fun showProfile() {
        SharedPref.getLoginUserId()?.let {
            GlobalScope.launch(Dispatchers.IO) {
                val userEntity = App.appDb?.dataUser()?.fetchUserById(it)
                GlobalScope.launch(Dispatchers.Main) {
                    if (userEntity != null) {
                        val user =
                            User(
                                userEntity.id,
                                userEntity.name,
                                userEntity.username,
                                userEntity.email
                            )
                        view.onShowSuccess(user)
                    } else {
                        view.onFailed((this as Context).getString(R.string.show_profile_failed))
                    }
                }
            }
        }
    }

    override fun verifyAndUpdate(
        name: String,
        username: String,
        email: String,
        pass: String
    ) {

        val id = SharedPref.getLoginUserId()
        val changedUser = id?.let { UserEntity(it, name, username, email, pass) }

        GlobalScope.launch(Dispatchers.IO) {
            val originUser = App.appDb?.dataUser()?.fetchUserById(id!!)
            if (originUser != null && originUser.password == pass) {
                val updated = App.appDb?.dataUser()?.updateUser(changedUser!!)

                launch(Dispatchers.Main) {
                    if (updated != null && updated > 1) {
                        view.onUpdateSuccess()
                    } else {
                        view.onFailed((this as Context).getString(R.string.update_failed))
                    }
                }
            } else {
                launch(Dispatchers.Main) {
                    view.onFailed((this as Context).getString(R.string.update_failed))
                }
            }
        }
    }
}
