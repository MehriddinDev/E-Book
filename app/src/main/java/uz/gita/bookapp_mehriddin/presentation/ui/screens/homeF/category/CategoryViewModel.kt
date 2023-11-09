package uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.category

import android.content.Context
import androidx.lifecycle.LiveData
import uz.gita.bookapp_mehriddin.data.BookData
import uz.gita.bookapp_mehriddin.data.source.local.room.entity.BookEntity

interface CategoryViewModel {
    val allBookLiveData:LiveData<List<BookEntity>>
    val booksByCategory : LiveData<List<BookEntity>>
    val openDescriptionScreen:LiveData<String>
    //val stateProgressLivedata:LiveData<Boolean>

    fun openDescriptionScreen(context: Context, bookData: BookData)
    fun getBooksByCategory(category:String)
}