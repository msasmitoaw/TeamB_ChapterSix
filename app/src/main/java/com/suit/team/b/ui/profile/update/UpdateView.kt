package com.suit.team.b.ui.profile.update

import com.suit.team.b.data.model.Users

interface UpdateView {
    fun onShowSuccess(user: Users)
    fun onUpdateSuccess()
    fun onFailed(toastString: String)
    fun onChangedDataReady(pass: String)
}
