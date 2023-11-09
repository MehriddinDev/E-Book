package uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.readbook.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import uz.gita.bookapp_mehriddin.data.source.local.room.entity.BookEntity
import java.io.File

interface ReadBookViewModel {

    val toastLiveData:LiveData<String>
    val setPdfViewLiveData:LiveData<File>

    fun findBook(name:String, context: Context)
    fun getPageCount():Int
    fun saveReadedPage(count:Int, bookName:String)
    fun getBook(name:String):BookEntity
}