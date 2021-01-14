package com.rockpaperscissors.team.b.utils

import android.content.Context
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.core.content.ContextCompat
import com.rockpaperscissors.team.b.R

fun setWord(context: Context, message: String) {
    makeText(context, message, Toast.LENGTH_SHORT).show()
    println(message)
}

fun ImageButton.onSelected(context: Context) =
    ContextCompat.getDrawable(context, R.drawable.ic_item_bg)
