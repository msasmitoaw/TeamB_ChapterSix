package com.suit.team.b.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.suit.team.b.data.model.RegisterRequest
import com.suit.team.b.data.model.RegisterResponse
import com.suit.team.b.data.remote.AuthApiService
import com.suit.team.b.utils.getErrorMessage
import com.suit.team.b.utils.getErrorThrowableCode
import com.suit.team.b.utils.getServiceErrorMsg
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RegisterViewModel(private val service: AuthApiService) : ViewModel() {

    private lateinit var disposable: Disposable
    private val resultRegister = MutableLiveData<RegisterResponse>()
    private val errorRegister = MutableLiveData<String>()

    fun onResultRegister(): LiveData<RegisterResponse> = resultRegister
    fun onErrorRegister(): LiveData<String> = errorRegister

    fun register(registerRequest: RegisterRequest) {
        disposable = service.register(registerRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(resultRegister::setValue) {
                errorRegister.value =
                    getErrorMessage(it.getServiceErrorMsg(), it.getErrorThrowableCode())
                it.printStackTrace()
            }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    class Factory(private val service: AuthApiService) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(service) as T
        }
    }
}
