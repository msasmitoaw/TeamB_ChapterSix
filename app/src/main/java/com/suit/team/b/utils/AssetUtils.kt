package com.suit.team.b.utils

import android.content.Context
import com.suit.team.b.App

fun Context.string(data: Int): String = getString(data)

fun getString(id: Int): String? {
    return App.weakReferenceContext.get()?.getString(id)
}
