package com.suit.team.b.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.suit.team.b.data.local.SharedPref
import com.suit.team.b.data.remote.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class SplashViewModel(private val service: ApiService, private val pref: SharedPref) :
    ViewModel() {

    private var disposable: Disposable? = null
    private val checkLogin = MutableLiveData<Boolean>()

    fun onCheckLogin(): LiveData<Boolean> = checkLogin

    fun checkIsLogin(): Boolean {
        disposable = service.me("Bearer" + pref.token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status != null) {
                    checkLogin.postValue(it.status)
                } else {
                    checkLogin.postValue(it.success)
                }

            }) {
                checkLogin.postValue(false)
                it.printStackTrace()
            }
        return pref.isLogin == true
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    class Factory(private val service: ApiService, private val pref: SharedPref) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SplashViewModel(service, pref) as T
        }
    }
}
