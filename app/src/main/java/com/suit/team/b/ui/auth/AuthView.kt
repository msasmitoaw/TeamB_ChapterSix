package com.suit.team.b.ui.auth

interface AuthView {
    fun onSuccess(id:Int)
    fun onError(msg: String)
}
