package uz.gita.bookapp_mehriddin.domain.usecase

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.bookapp_mehriddin.data.BookData
import uz.gita.bookapp_mehriddin.domain.repastory.AppRepastory

class BookUsecaseImpl(private val repastory:AppRepastory):BookUsecase {
    override fun getAllBooks(): Flow<Result<List<BookData>>> = flow {
       val k = repastory.getAllBook()
        emit(k)
    }.catch {
        emit(Result.failure(it))
    }
        .flowOn(Dispatchers.IO)

    override fun downLoadFile(context: Context,bookData: BookData): Flow<Result<String>> = flow{
        emit(repastory.downloadfayl1(context,bookData))
    }.catch {
        emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)

    /*override fun getSavedBooks(): List<BookData> {
        val allBooks = ArrayList<BookData>()
        val gson = Gson()
        val books = repastory.getSavedBooks()
        books?.let { bookss ->
            val list: List<String> = bookss.split("#")
            list.forEach {
                Log.d("JJJ", "getBook: $it")
                if (it.isNotBlank()) {
                    val book: BookData = gson.fromJson(it, BookData::class.java)
                    allBooks.add(book)
                }
            }
        }
        return allBooks
    }*/

    override fun getBooksByCategory(categoryName: String): Flow<Result<List<BookData>>> {
        return repastory.getBooksByCategory(categoryName)
    }

   /* override fun getPageCount(): Int {
       return repastory.getPagecount()
    }*/


}