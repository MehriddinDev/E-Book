package uz.data.source.local.room.database

import android.content.Context
import android.provider.ContactsContract.Data
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.data.source.local.room.dao.BookDao
import uz.data.source.local.room.entity.BookEntity

@androidx.room.Database(entities = [BookEntity::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun getBookDao(): BookDao

    companion object{
        private var database: Database? = null

        fun init(context: Context){
            if (database == null){
                database = Room.databaseBuilder(context.applicationContext, Database::class.java,"BookDatabase").allowMainThreadQueries().build()
            }
        }

        fun getInstance(): Database = database!!
    }
}