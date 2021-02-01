package com.suit.team.b.ui.register

interface RegisterNavigator {
    fun onSuccess(msg: String)
    fun onError(msg: String, errCode: Int = 0)
}
