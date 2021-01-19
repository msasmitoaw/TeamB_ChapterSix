package com.suit.team.b.ui.auth

interface AuthenticView {
    fun onSuccess(id:Int)
    fun onError(msg: String)
}