package uz.data.source.local.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import uz.data.source.local.room.entity.BookEntity

@Dao
interface BookDao {

    @Query("SELECT * FROM books WHERE download = 1")
    fun getAllBookDownloaded():List<BookEntity>

    @Query("SELECT * FROM books")
    fun getAllBook():List<BookEntity>

    @Query("SELECT * FROM books")
    fun getAllBookUnDownloaded():LiveData<List<BookEntity>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun deleteBook(bookEntity: BookEntity):Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(bookEntity: BookEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(bookEntity: List<BookEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(bookEntity: BookEntity)

    @Query("SELECT * FROM books WHERE title LIKE '%' || :name || '%'")
    fun getBookByName(name: String): List<BookEntity>
}