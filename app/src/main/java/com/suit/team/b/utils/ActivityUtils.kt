package com.suit.team.b.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.auth0.android.jwt.JWT
import com.suit.team.b.R
import com.suit.team.b.data.local.SharedPref
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun setWord(context: Context, message: String) {
    makeText(context, message.replace("\n", " "), Toast.LENGTH_SHORT).show()
    val activity = context as Activity
    Log.d(activity.localClassName, message.replace("\n", " "))
}

fun View.onSelected(context: Context) {
    background = ContextCompat.getDrawable(
        context,
        R.drawable.item_bg
    )
}

fun EditText.text(): String = text.toString()

fun getFromToken(claimName: String?): String? {
    val jwt = SharedPref.token?.let { JWT(it) }
    return claimName?.let { jwt?.getClaim(it)?.asString() }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDateFromIso(isoInstantDate: String, formatStyle: FormatStyle): String {
    val f: DateTimeFormatter = DateTimeFormatter.ISO_INSTANT
    val date = Instant.from(f.parse(isoInstantDate))
    val ldt = LocalDateTime.ofInstant(date, ZoneId.systemDefault())
    return ldt.format(
        DateTimeFormatter.ofLocalizedDateTime(formatStyle).withZone(
            ZoneId.systemDefault()
        )
    )
}
