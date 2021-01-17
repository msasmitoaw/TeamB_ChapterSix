package com.suit.team.b.ui.profile.update

interface UpdatePresenter {
    fun showProfile()
    fun verifyAndUpdate(name: String, username: String, email: String, pass: String)
}
