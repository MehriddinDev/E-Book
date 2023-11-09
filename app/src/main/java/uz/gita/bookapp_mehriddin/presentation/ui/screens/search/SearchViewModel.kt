package uz.gita.bookapp_mehriddin.presentation.ui.screens.search

import androidx.lifecycle.LiveData
import uz.gita.bookapp_mehriddin.data.source.local.room.entity.BookEntity

interface SearchViewModel {
    val allBooksLiveData : LiveData<List<BookEntity>>
    val booksByQueryLivedata:LiveData<List<BookEntity>>

    fun getBookByQuery(query:String)
    fun workDao()
    fun deleteBook(bookEntity: BookEntity)
}