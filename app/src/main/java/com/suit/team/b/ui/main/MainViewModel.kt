package com.suit.team.b.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.suit.team.b.data.local.SharedPref
import com.suit.team.b.ui.splash.SplashViewModel
import io.reactivex.disposables.Disposable

class MainViewModel :
        ViewModel()  {

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
            return MainViewModel() as T
        }
    }
}