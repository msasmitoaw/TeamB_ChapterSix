package com.suit.team.b

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.suit.team.b.data.db.AppDb

class App : Application() {
    companion object {
        var context: Context? = null
        var appDb: AppDb? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        appDb = Room
            .databaseBuilder(
                applicationContext,
                AppDb::class.java,
                "SuitDb"
            ).build()
    }
}
