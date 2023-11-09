package uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.category

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.bookapp_mehriddin.data.BookData
import uz.gita.bookapp_mehriddin.data.source.local.room.database.MyDatabase
import uz.gita.bookapp_mehriddin.data.source.local.room.entity.BookEntity
import uz.gita.bookapp_mehriddin.domain.repastory.AppRepastory
import uz.gita.bookapp_mehriddin.utils.myLog

class CategoryViewModelImpl : ViewModel(), CategoryViewModel {
    private val repastory = AppRepastory.getInstance()
    private val dao = MyDatabase.getInstance().getBookDao()

    override val allBookLiveData: LiveData<List<BookEntity>>
        get() = dao.getAllBookUnDownloaded()

    override val booksByCategory = MutableLiveData<List<BookEntity>>()


    override val openDescriptionScreen = MutableLiveData<String>()
    // override val stateProgressLivedata = MutableLiveData<Boolean>()

    override fun openDescriptionScreen(context: Context, bookData: BookData) {
        myLog("openReadScreen", "KKJ")
        if (bookData.download) {
            myLog("openReadScreen true", "KKJ")
            openDescriptionScreen.value = bookData.name
        } else {
            myLog("openReadScreen false", "KKJ")
            downLoadBook(context, bookData)
        }
    }

    override fun getBooksByCategory(category: String) {
        if (category == "All") {
            dao.update(dao.getAllBook()[0])
        } else {
            booksByCategory.value = dao.getBooksByCategory(category)
        }
    }

    private fun downLoadBook(context: Context, book: BookData) {
        // stateProgressLivedata.value = true
        repastory.downloadFile(context, book).onEach {
            it.onSuccess {
                //   stateProgressLivedata.value = false
                openDescriptionScreen.value = book.name
            }
            it.onFailure {
                //  stateProgressLivedata.value = false
            }
        }.launchIn(viewModelScope)
    }


}