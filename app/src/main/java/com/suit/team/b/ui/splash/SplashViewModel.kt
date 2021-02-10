package com.suit.team.b.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.suit.team.b.data.local.SharedPref
import io.reactivex.disposables.Disposable

class SplashViewModel :
    ViewModel() {

    private var disposable: Disposable? = null

    fun checkIsLogin(): Boolean = SharedPref.isLogin == true

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    class Factory :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SplashViewModel() as T
        }
    }
}
