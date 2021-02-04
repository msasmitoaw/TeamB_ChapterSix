package com.suit.team.b.ui.profile.show

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suit.team.b.data.local.SharedPref
import com.suit.team.b.data.model.UsersResponse
import com.suit.team.b.data.remote.ApiModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ProfileViewModel : ViewModel() {

    private var disposable: Disposable? = null
    private var service = ApiModule.service
    val resultRegister = MutableLiveData<UsersResponse>()

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    fun fetchUserData() {
        disposable = service.getUserData(SharedPref.token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                resultRegister.value = it
            }, {
                it.printStackTrace()
            })
    }

    fun logout() {
        SharedPref.logout()
    }
}
