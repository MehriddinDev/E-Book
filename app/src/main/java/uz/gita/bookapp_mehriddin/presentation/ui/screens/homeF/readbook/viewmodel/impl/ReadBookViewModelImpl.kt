package uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.readbook.viewmodel.impl

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.bookapp_mehriddin.data.source.local.room.database.MyDatabase
import uz.gita.bookapp_mehriddin.data.source.local.room.entity.BookEntity
import uz.gita.bookapp_mehriddin.domain.repastory.AppRepastory
import uz.gita.bookapp_mehriddin.domain.usecase.BookUsecaseImpl
import uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.readbook.viewmodel.ReadBookViewModel
import java.io.File

class ReadBookViewModelImpl() : ViewModel(), ReadBookViewModel {
    private val repastory = AppRepastory.getInstance()
    private val usecase = BookUsecaseImpl(AppRepastory.getInstance())
    private val dao = MyDatabase.getInstance().getBookDao()
    override val toastLiveData = MutableLiveData<String>()
    override val setPdfViewLiveData = MutableLiveData<File>()

    override fun findBook(name: String, context: Context) {
        val file = File(context.filesDir, name)
        if (file.exists()) {
            setPdfViewLiveData.value = file
        } else {
            toastLiveData.value = "file not found"
        }
    }

    override fun getPageCount(): Int {
        //return usecase.getPageCount()
        return 0
    }

    override fun saveReadedPage(count: Int, bookName: String) {
        val b = dao.getBooksByName(bookName)
        dao.saveReadedPage(
            BookEntity(
                b.name,
                b.author,
                b.title,
                b.imgUrl,
                b.desc,
                b.download,
                b.pages,
                count.toLong(),
                b.genre
            )
        )
    }

    override fun getBook(name: String): BookEntity {
        return dao.getBooksByName(name)
    }
}