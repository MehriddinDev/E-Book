package uz.gita.bookapp_mehriddin.data.source.local.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import uz.gita.bookapp_mehriddin.data.source.local.room.entity.BookEntity

@Dao
interface BookDao {

    @Query("SELECT * FROM books WHERE download = 1")
    fun getAllBookDownloaded(): List<BookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(books: List<BookEntity>)

    @Query("SELECT * FROM books")
    fun getAllBookUnDownloaded(): LiveData<List<BookEntity>>

    @Query("SELECT * FROM books WHERE title LIKE '%' || :name || '%'")
    fun getBookByName(name: String): List<BookEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun deleteBook(bookEntity: BookEntity): Int

    @Query("SELECT * FROM books")
    fun getAllBook(): List<BookEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(bookEntity: BookEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun saveReadedPage(bookEntity: BookEntity)

    @Query("SELECT * FROM books WHERE genre = :category")
    fun getBooksByCategory(category: String): List<BookEntity>

    @Query("SELECT * FROM books WHERE name = :name")
    fun getBooksByName(name: String):BookEntity

}