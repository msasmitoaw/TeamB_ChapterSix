package com.suit.team.b.ui.profile.show

import com.suit.team.b.App
import com.suit.team.b.R
import com.suit.team.b.data.local.SharedPref
import com.suit.team.b.data.model.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfilePresenterImp(private val view: ProfileView) : ProfilePresenter {

    private val appDb = App.appDb
    private val appContext = App.context

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
                    appContext?.let { view.onShowFailed(it.getString(R.string.show_profile_failed)) }
                }
            }
        }
    }

    override fun deleteProfile() {
        val id = SharedPref.id
        if (id != null) {
            GlobalScope.launch(Dispatchers.IO) {
                val userEntity = appDb?.dataUser()?.deleteUserById(id)
                launch(Dispatchers.Main) {
                    if (userEntity != null) {
                        appContext?.getString(R.string.profile_del_success)?.let {
                            view.onDeleteSuccess(
                                it
                            )
                        }
                    } else {
                        appContext?.getString(R.string.profile_del_failed)?.let {
                            view.onDeleteFailed(
                                it
                            )
                        }
                    }
                }
            }
        } else {
            appContext?.getString(R.string.profile_del_failed)?.let { view.onDeleteFailed(it) }
        }
    }

    override fun logout() {
        SharedPref.logout()
    }
}
