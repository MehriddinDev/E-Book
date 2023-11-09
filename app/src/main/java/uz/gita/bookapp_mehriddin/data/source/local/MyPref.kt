package uz.gita.bookapp_mehriddin.data.source.local

import android.content.Context
import uz.gita.bookapp_mehriddin.app.App

class MyPref {
    private val pref = App.context.getSharedPreferences("MyPref",Context.MODE_PRIVATE)
    private val edit = pref.edit()

    companion object {
        private lateinit var myPref: MyPref

        fun getInstance(): MyPref {
            if (!::myPref.isInitialized) {
                myPref = MyPref()
            }
            return myPref
        }
    }

    fun getSaveFirst():Boolean{
       return pref.getBoolean("first",true)
    }

    fun saveFirst(bool:Boolean){
        edit.putBoolean("first",bool).apply()
    }
}