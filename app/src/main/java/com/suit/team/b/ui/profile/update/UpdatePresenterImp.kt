package com.suit.team.b.ui.profile.update

import com.suit.team.b.App
import com.suit.team.b.R
import com.suit.team.b.data.db.UserEntity
import com.suit.team.b.data.local.SharedPref
import com.suit.team.b.data.model.Users
import com.suit.team.b.utils.string
import kotlinx.coroutines.*

class UpdatePresenterImp(private val view: UpdateView) : UpdatePresenter {

    private val appDb = App.appDb
    private val appContext = App.weakReferenceContext.get()

    override fun fetchUser() {
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
                    view.onFetchSuccess(user)
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

    override fun checkDuplicate(username: String, email: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val countUname = withContext(Dispatchers.IO) {
                appDb?.dataUser()?.countDuplicate(SharedPref.id!!, username, "")
            }
            val countEmail = withContext(Dispatchers.IO) {
                appDb?.dataUser()?.countDuplicate(SharedPref.id!!, "", email)
            }
            if (countUname != null && countUname > 0) {
                view.onFailed(appContext?.string(R.string.username_exist)!!)
            } else if (countEmail != null && countEmail > 0) {
                view.onFailed(appContext?.string(R.string.email_exist)!!)
            } else {
                view.onNoDuplicate()
            }
        }

    }

    override fun verifyAndUpdate(
        name: String,
        username: String,
        email: String,
        pass: String,
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
                                it?.string(R.string.update_failed)!!
                            )
                        }
                    }
                }
            } else {
                GlobalScope.launch(Dispatchers.Main) {
                    appContext.let {
                        view.onFailed(
                            it?.string(R.string.update_failed)!!
                        )
                    }
                }
            }
        }
    }

}
