package com.suit.team.b.ui.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonParser
import com.suit.team.b.R
import com.suit.team.b.data.model.RegisterRequest
import com.suit.team.b.data.remote.AuthApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.adapter.rxjava2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.HttpsURLConnection

class RegisterViewModel(private val service: AuthApiService) : ViewModel() {

    private lateinit var disposable: Disposable
    lateinit var navigator: RegisterNavigator

    fun register(registerRequest: RegisterRequest) {
        if (registerRequest.username.isBlank()) {
            navigator.onError(R.string.username_validation.toString())
            return
        }
        if (registerRequest.password.isBlank()) {
            navigator.onError(R.string.password_validation.toString())
            return
        }
        if (registerRequest.email.isBlank()) {
            navigator.onError(R.string.email_validation.toString())
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(registerRequest.email).matches()) {
            navigator.onError(R.string.email_format_validation.toString())
            return
        }

        disposable = service.register(registerRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                navigator.onSuccess(R.string.sign_up_success.toString())
            }) {
                navigator.onError(getServiceErrorMsg(it), getErrorThrowableCode(it))
                it.printStackTrace()
            }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    private fun getServiceErrorMsg(error: Throwable): String {
        var message = "Sorry there a problem in our services"
        message = when (error) {
            is HttpException -> {
                try {
                    val errorJsonString = error.response()
                        ?.errorBody()?.string()
                    JsonParser().parse(errorJsonString)
                        .asJsonObject["errors"]
                        .asString
                } catch (e: Exception) {
                    message
                }
            }
            is UnknownHostException -> "Unknown Error"
            is ConnectException -> "No internet connected"
            is SocketTimeoutException -> "No internet connected"
            else -> error.message ?: message
        }
        return message
    }

    private fun getErrorThrowableCode(error: Throwable?): Int = when (error) {
        is HttpException ->
            when (error.code()) {
                HttpsURLConnection.HTTP_UNAUTHORIZED -> 401
                HttpsURLConnection.HTTP_NOT_FOUND -> 404
                HttpsURLConnection.HTTP_INTERNAL_ERROR -> 500
                HttpsURLConnection.HTTP_BAD_REQUEST -> 400
                HttpsURLConnection.HTTP_FORBIDDEN -> 403
                HttpsURLConnection.HTTP_CONFLICT -> 409
                else -> error.code()
            }
        else -> 500
    }

    class Factory(private val service: AuthApiService) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(service) as T
        }
    }
}
