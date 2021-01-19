package com.suit.team.b

import android.app.Application
import android.content.Context
import java.lang.ref.WeakReference
import androidx.room.Room
import com.suit.team.b.data.db.AppDb

class App : Application() {
    companion object {
        lateinit var weakReferenceContext: WeakReference<Context>
        var context: Context? = null
        var appDb: AppDb? = null
    }

    override fun onCreate() {
        super.onCreate()
        weakReferenceContext = WeakReference(applicationContext)
        context = applicationContext

        appDb = Room
            .databaseBuilder(
                applicationContext,
                AppDb::class.java,
                "SuitDb"
            ).build()
    }
}
