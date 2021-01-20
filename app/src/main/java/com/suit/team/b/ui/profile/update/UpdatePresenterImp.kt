package com.suit.team.b.ui.profile.update

import com.suit.team.b.App
import com.suit.team.b.R
import com.suit.team.b.data.db.UserEntity
import com.suit.team.b.data.local.SharedPref
import com.suit.team.b.data.model.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdatePresenterImp(private val view: UpdateView) : UpdatePresenter {

    private val appDb = App.appDb
    private val appContext = App.weakReferenceContext.get()

    override fun showProfile() {
        val id = SharedPref.id
        GlobalScope.launch(Dispatchers.IO) {
            val userEntity = appDb?.dataUser()?.fetchUserById(id!!)
            GlobalScope.launch(Dispatchers.Main) {
                if (userEntity != null) {
                    val user =
                        Users(
                            userEntity.id,
                            userEntity.name,
                            userEntity.password,
                            userEntity.email,
                            userEntity.username
                        )
                    view.onShowSuccess(user)
                } else {
                    appContext.let {
                        view.onFailed(
                            it?.getString(R.string.show_profile_failed).toString()
                        )
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

        val currentId = SharedPref.id
        val currentUName = SharedPref.username

        val newData = currentId?.let { UserEntity(it, name, pass, email, username) }

        GlobalScope.launch(Dispatchers.IO) {
            val currentUser = appDb?.dataUser()?.checkUserPassword(currentUName!!, pass)
            if (currentUser != null && currentUser.id == currentId) {

                val updated = appDb?.dataUser()?.updateUser(newData!!)

                GlobalScope.launch(Dispatchers.Main) {
                    if (updated != null && updated == 1) {
                        SharedPref.username = username
                        SharedPref.name = name
                        view.onUpdateSuccess()
                    } else {
                        appContext.let {
                            view.onFailed(
                                it?.getString(R.string.update_failed).toString()
                            )
                        }
                    }
                }
            } else {
                GlobalScope.launch(Dispatchers.Main) {
                    appContext.let {
                        view.onFailed(
                            it?.getString(R.string.update_failed).toString()
                        )
                    }
                }
            }
        }
    }
}
