package uz.gita.bookapp_mehriddin.presentation.ui.screens.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.bookapp_mehriddin.data.source.local.room.database.MyDatabase
import uz.gita.bookapp_mehriddin.data.source.local.room.entity.BookEntity
import uz.gita.bookapp_mehriddin.utils.myLog

class SearchViewModelImpl:SearchViewModel,ViewModel() {
    private val db = MyDatabase.getInstance().getBookDao()
    override val allBooksLiveData: LiveData<List<BookEntity>>
        get() = db.getAllBookUnDownloaded()
    override val booksByQueryLivedata = MutableLiveData<List<BookEntity>>()

    override fun getBookByQuery(query: String) {

        booksByQueryLivedata.value = db.getBookByName(query)
    }


    override fun deleteBook(bookEntity: BookEntity) {
        myLog("delete = SearchViewModelImpl","MKMM")
        val k = db.deleteBook(
            BookEntity(
                bookEntity.name,
                bookEntity.author,
                bookEntity.title,
                bookEntity.imgUrl,
                bookEntity.desc,
                false,
                bookEntity.pages,
                0,
                bookEntity.genre
            )
        )
        myLog("num = $k","LLLLL")

    }

    override fun workDao() {
        db.update(db.getAllBook()[0])
    }

}