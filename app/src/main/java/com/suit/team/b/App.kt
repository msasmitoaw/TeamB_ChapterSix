package com.suit.team.b

import android.app.Application
import android.content.Context
import java.lang.ref.WeakReference

class App : Application() {
    companion object {
        lateinit var weakReferenceContext: WeakReference<Context>
    }

    override fun onCreate() {
        super.onCreate()
        weakReferenceContext = WeakReference(applicationContext)
    }
}
