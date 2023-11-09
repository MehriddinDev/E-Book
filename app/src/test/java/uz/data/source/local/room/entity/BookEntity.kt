package uz.data.source.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.data.BookData

@Entity("books")
data class BookEntity(
    @PrimaryKey
    val name:String,
    val author:String,
    val title:String,
    val imgUrl:String,
    val desc:String,
    val download:Boolean,
    val pages:Long,
    val readPage:Long,
    val genre:String
){
    fun toData() = BookData(name, author, title, imgUrl, desc,download,pages,readPage,genre)
}
