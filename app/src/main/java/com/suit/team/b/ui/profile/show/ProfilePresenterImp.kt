package com.suit.team.b.ui.profile.show

import android.content.Context
import com.suit.team.b.App
import com.suit.team.b.R
import com.suit.team.b.data.local.SharedPref
import com.suit.team.b.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfilePresenterImp(private val view: ProfileView) : ProfilePresenter {

    private val appDb = App.appDb

    override fun showProfile() {
        GlobalScope.launch(Dispatchers.IO) {
            val userEntity =
                SharedPref.getLoginUserId()?.let { appDb?.dataUser()?.fetchUserById(it) }
            launch(Dispatchers.Main) {
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
                    view.onShowFailed((this as Context).getString(R.string.show_profile_failed))
                }
            }
        }
    }

    override fun deleteProfile() {
        val id = SharedPref.getLoginUserId()
        if (id != null) {
            GlobalScope.launch(Dispatchers.IO) {
                val userEntity = appDb?.dataUser()?.deleteUserById(id)
                launch(Dispatchers.Main) {
                    if (userEntity != null) {
                        view.onDeleteSuccess((this as Context).getString(R.string.profile_del_success))
                    } else {
                        view.onDeleteFailed((this as Context).getString(R.string.profile_del_failed))
                    }
                }
            }
        } else {
            view.onDeleteFailed((view as Context).getString(R.string.profile_del_failed))
        }
    }

    override fun logout() {
        SharedPref.logout()
    }
}
