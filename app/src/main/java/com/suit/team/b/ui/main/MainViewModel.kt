package com.suit.team.b.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.suit.team.b.data.local.SharedPref
import com.suit.team.b.data.model.LoginRequest
import com.suit.team.b.data.remote.ApiService
import com.suit.team.b.utils.getFromToken
import com.suit.team.b.utils.getServiceErrorMsg
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val service: ApiService) : ViewModel() {
    private var disposable: Disposable? = null
    private val errorResponse = MutableLiveData<String>()
    fun onErrorResponse(): LiveData<String> = errorResponse

    fun startRenewToken() {
        val unixTime = (System.currentTimeMillis() / 1000L) + (30 * 60)
        disposable = service.me("Bearer " + SharedPref.token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }) {
                if (unixTime > getFromToken("exp")!!.toLong()) updateToken()
                if (it.getServiceErrorMsg() == "Invalid Token") updateToken()
                it.printStackTrace()
            }
    }

    private fun updateToken() {
        val loginRequest = LoginRequest(
            SharedPref.email.toString(),
            SharedPref.password.toString()
        )
        disposable = service.login(loginRequest = loginRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                SharedPref.token = it.data.token
            }) {
                errorResponse.value = it.getServiceErrorMsg()
                it.printStackTrace()
            }
    }

    fun logout() {
        SharedPref.logout()
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    class Factory(private val service: ApiService) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(service) as T
        }
    }
}
