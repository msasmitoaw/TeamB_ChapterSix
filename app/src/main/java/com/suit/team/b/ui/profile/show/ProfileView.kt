package com.rockpaperscissors.team.b.ui.profile.show

interface ProfileView {
    fun onSuccess(toastString: String)
    fun onFailed(toastString: String)
}