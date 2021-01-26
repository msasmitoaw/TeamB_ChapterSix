package com.suit.team.b.ui.profile.update

import com.suit.team.b.data.model.Users

interface UpdateView {
    fun onFetchSuccess(user: Users)
    fun onUpdateSuccess()
    fun onPasswordEntered(pass: String)
    fun onNoDuplicate()
    fun onFailed(toastString: String)

}
