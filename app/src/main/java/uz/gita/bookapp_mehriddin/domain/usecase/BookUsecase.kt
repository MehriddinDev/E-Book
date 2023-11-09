package uz.gita.bookapp_mehriddin.domain.usecase

import android.content.Context
import kotlinx.coroutines.flow.Flow
import uz.gita.bookapp_mehriddin.data.BookData

interface BookUsecase {
    fun getAllBooks(): Flow<Result<List<BookData>>>
    fun downLoadFile(context: Context, bookData: BookData):Flow<Result<String>>
    //fun getSavedBooks(): List<BookData>
    fun getBooksByCategory(categoryName: String): Flow<Result<List<BookData>>>
   // fun getPageCount():Int
}