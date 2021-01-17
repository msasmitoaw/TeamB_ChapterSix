package com.suit.team.b.ui.profile.show

import com.suit.team.b.data.model.User

interface ProfileView {
    fun onShowSuccess(user: User)
    fun onShowFailed(toastString: String)
    fun onDeleteSuccess(toastString: String)
    fun onDeleteFailed(toastString: String)
}
