package uz.gita.bookapp_mehriddin.app

import android.app.Application
import android.content.Context
import uz.gita.bookapp_mehriddin.data.source.local.room.database.MyDatabase

class App:Application() {
    companion object{
        lateinit var context:Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        MyDatabase.init(this)
    }
}