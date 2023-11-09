package uz.gita.bookapp_mehriddin.data.source.local.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.gita.bookapp_mehriddin.data.source.local.room.dao.BookDao
import uz.gita.bookapp_mehriddin.data.source.local.room.entity.BookEntity

@Database(entities = [BookEntity::class], version = 1, exportSchema = false)
abstract class MyDatabase :RoomDatabase(){
    abstract fun getBookDao():BookDao

    companion object {
        private const val NAME_DATABASE = "databaseBook.db"
        private lateinit var database: MyDatabase

        fun init(context: Context){
            if(!(::database.isInitialized)){
                database = Room.databaseBuilder(context, MyDatabase::class.java, NAME_DATABASE)
                    .allowMainThreadQueries()
                    .build()
            }
        }
        fun getInstance() = database
    }
}