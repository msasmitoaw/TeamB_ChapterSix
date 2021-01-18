package com.suit.team.b.ui.profile.update

import com.suit.team.b.App
import com.suit.team.b.R
import com.suit.team.b.data.db.UserEntity
import com.suit.team.b.data.local.SharedPref
import com.suit.team.b.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdatePresenterImp(private val view: UpdateView) : UpdatePresenter {

    private val appDb = App.appDb
    private val appContext = App.context

    override fun showProfile() {
        val id = SharedPref.getLoginUserId()
        GlobalScope.launch(Dispatchers.IO) {
            val userEntity = appDb?.dataUser()?.fetchUserById(id!!)
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
                    appContext?.let { view.onFailed(it.getString (R.string.show_profile_failed)) }
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
        val inputUser = id?.let { UserEntity(it, name, username, email, pass) }

        GlobalScope.launch(Dispatchers.IO) {
            val originUser = appDb?.dataUser()?.fetchUserById(id!!)
            if (originUser != null && originUser.password == pass) {
                val updated = appDb?.dataUser()?.updateUser(inputUser!!)

                GlobalScope.launch(Dispatchers.Main) {
                    if (updated != null && updated == 1) {
                        view.onUpdateSuccess()
                    } else {
                        appContext?.let { view.onFailed(it.getString(R.string.update_failed)) }
                    }
                }
            } else {
                GlobalScope.launch(Dispatchers.Main) {
                    appContext?.let { view.onFailed(it.getString(R.string.update_failed)) }
                }
            }
        }
    }
}
