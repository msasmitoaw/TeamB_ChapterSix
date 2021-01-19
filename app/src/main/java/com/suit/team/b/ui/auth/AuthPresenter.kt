package com.suit.team.b.ui.auth

interface AuthPresenter {
    fun login(username: String, password: String)
    fun checkIsLogin(): Boolean
    fun getId():Int?
}
