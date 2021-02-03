package com.suit.team.b.utils

import com.suit.team.b.R
import org.json.JSONObject
import retrofit2.adapter.rxjava2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.HttpsURLConnection

fun Throwable.getServiceErrorMsg(): String {
    var messages = "Sorry there a problem in our services"
    messages = when (this) {
        is HttpException -> {
            try {
                val errorJsonString = JSONObject(response()?.errorBody()!!.string())
                errorJsonString.getString("errors")
            } catch (e: Exception) {
                messages
            }
        }
        is UnknownHostException -> "Unknown Error"
        is ConnectException -> "No internet connected"
        is SocketTimeoutException -> "No internet connected"
        else -> {
            message.toString()
        }
    }
    return messages
}

fun Throwable.getErrorThrowableCode(): Int = when (this) {
    is HttpException ->
        when (code()) {
            HttpsURLConnection.HTTP_UNAUTHORIZED -> 401
            HttpsURLConnection.HTTP_NOT_FOUND -> 404
            HttpsURLConnection.HTTP_INTERNAL_ERROR -> 500
            HttpsURLConnection.HTTP_BAD_REQUEST -> 400
            HttpsURLConnection.HTTP_FORBIDDEN -> 403
            HttpsURLConnection.HTTP_CONFLICT -> 409
            else -> code()
        }
    else -> 500
}

fun getErrorMessage(msg: String, errCode: Int = 0): String = when (errCode) {
    400 -> R.string.email_format_validation.toString()
    422 -> when {
        msg.contains("username_1 dup key") -> R.string.username_exist.toString()
        msg.contains("email_1 dup key") -> R.string.email_exist.toString()
        msg.contains("alphanumeric") -> R.string.username_not_alphanumeric.toString()
        msg.contains("shorter") -> R.string.username_shorter.toString()
        else -> msg
    }
    else -> msg
}
