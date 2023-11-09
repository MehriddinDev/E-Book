package uz.data.source.local

import android.content.Context
import android.util.Log
import uz.gita.bookapp_mehriddin.app.App

class MyPref private constructor() {
    private val pref = App.context.getSharedPreferences("Book", Context.MODE_PRIVATE)
    private val edit = pref.edit()

    companion object {
        private lateinit var myPref: MyPref

        fun getInstance(): MyPref {
            if (!Companion::myPref.isInitialized) {
                myPref = MyPref()
            }
            return myPref
        }
    }

    fun saveBook(book: String) {
        Log.d("JJJ", "saveBook: $book")
        val s = "${getBooks()}#$book"
        edit.putString("book", s).apply()
    }

    fun getBooks(): String? {
        val f = pref.getString("book", "")
        return f
    }

    fun savePageCount(page:Int){
        Log.d("MMMM", "savePageCount: $page")
        edit.putInt("page",page).apply()
    }

    fun getPageCount():Int{
        return pref.getInt("page",0)
    }

    fun saveFirst(bool:Boolean){
        edit.putBoolean("first",bool).apply()
    }

    fun getSaveFirst():Boolean{
        return pref.getBoolean("first",true)
    }

    fun returnFirst(bool:Boolean){
        edit.putBoolean("rett",bool).apply()
    }

    fun getReturnFirst():Boolean{
        return pref.getBoolean("rett",false)
    }
}