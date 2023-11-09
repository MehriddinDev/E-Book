package uz.data

import android.os.Parcel
import android.os.Parcelable
import uz.data.source.local.room.entity.BookEntity

data class BookData(
    val name:String,
    val author:String,
    val title:String,
    val imgUrl:String,
    val desc:String,
    val download:Boolean,
    val pages:Long,
    val readPage:Long,
    val genre:String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeString(imgUrl)
        parcel.writeString(desc)
        parcel.writeByte(if (download) 1 else 0)
        parcel.writeLong(pages)
        parcel.writeLong(readPage)
        parcel.writeString(genre)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BookData> {
        override fun createFromParcel(parcel: Parcel): BookData {
            return BookData(parcel)
        }

        override fun newArray(size: Int): Array<BookData?> {
            return arrayOfNulls(size)
        }
    }
    fun toEntity() = BookEntity(name, author, title, imgUrl, desc, download, pages,readPage,genre)
}







