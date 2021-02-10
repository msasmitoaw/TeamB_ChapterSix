package com.suit.team.b.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.suit.team.b.data.local.SharedPref
import com.suit.team.b.data.model.LoginRequest
import com.suit.team.b.data.model.LoginResponse
import com.suit.team.b.data.remote.ApiService
import com.suit.team.b.utils.getErrorMessage
import com.suit.team.b.utils.getErrorThrowableCode
import com.suit.team.b.utils.getServiceErrorMsg
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class AuthViewModel(private val service: ApiService, private val pref: SharedPref) :
    ViewModel() {

    private var disposable: Disposable? = null
    private val resultLogin = MutableLiveData<LoginResponse>()
    private val errorLogin = MutableLiveData<String>()
    private val checkLogin = MutableLiveData<Boolean>()

    fun onResultLogin(): LiveData<LoginResponse> = resultLogin
    fun onCheckLogin(): LiveData<Boolean> = checkLogin
    fun onErrorLogin(): LiveData<String> = errorLogin

    fun login(loginRequest: LoginRequest) {
        disposable = service.login(loginRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                pref.token = it.data.token
                pref.isLogin = true
                pref.username = it.data.username
                pref.email = it.data.email
                pref.password = loginRequest.password
                resultLogin.value = it
            }) {
                errorLogin.value =
                    getErrorMessage(it.getServiceErrorMsg(), it.getErrorThrowableCode())
                it.printStackTrace()
            }
    }

    fun checkIsLogin() {
        disposable = service.me("Bearer" + pref.token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ checkLogin.value = it.success }) {
                it.printStackTrace()
            }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    class Factory(private val service: ApiService, private val pref: SharedPref) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(service, pref) as T
        }
    }
}
