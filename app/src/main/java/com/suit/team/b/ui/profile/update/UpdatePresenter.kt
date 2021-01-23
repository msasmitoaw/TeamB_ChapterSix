package com.suit.team.b.ui.profile.update

interface UpdatePresenter {
    fun fetchUser()
    fun checkDuplicate(username: String, email: String)
    fun verifyAndUpdate(name: String, username: String, email: String, pass: String)
}
