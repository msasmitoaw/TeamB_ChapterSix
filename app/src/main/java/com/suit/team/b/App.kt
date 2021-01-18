package com.suit.team.b

import android.app.Application
import android.content.Context
<<<<<<< HEAD
import androidx.room.Room
import com.suit.team.b.data.db.User

class App : Application() {
    companion object {
        var context: Context? = null
        var appDb: User? = null
=======
import java.lang.ref.WeakReference

class App : Application() {
    companion object {
        lateinit var weakReferenceContext: WeakReference<Context>
>>>>>>> c8eb7da068e201b916c789936876cf65d154b7b9
    }

    override fun onCreate() {
        super.onCreate()
<<<<<<< HEAD
        context = applicationContext

        appDb = Room
            .databaseBuilder(
                applicationContext,
                User::class.java,
                "MyDb"
            ).build()
=======
        weakReferenceContext = WeakReference(applicationContext)
>>>>>>> c8eb7da068e201b916c789936876cf65d154b7b9
    }
}
