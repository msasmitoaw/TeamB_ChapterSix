package com.suit.team.b.ui.profile.show

import com.suit.team.b.data.model.Users

interface ProfileView {
    fun onShowSuccess(user: Users)
    fun onShowFailed(toastString: String)
    fun onDeleteSuccess(toastString: String)
    fun onDeleteFailed(toastString: String)
}
