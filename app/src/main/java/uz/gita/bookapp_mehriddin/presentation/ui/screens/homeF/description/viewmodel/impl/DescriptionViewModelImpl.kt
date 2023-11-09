package uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.description.viewmodel.impl

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.bookapp_mehriddin.app.App
import uz.gita.bookapp_mehriddin.data.BookData
import uz.gita.bookapp_mehriddin.data.source.local.room.database.MyDatabase
import uz.gita.bookapp_mehriddin.data.source.local.room.entity.BookEntity
import uz.gita.bookapp_mehriddin.domain.repastory.AppRepastory
import uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.description.viewmodel.DescriptionViewModel
import uz.gita.bookapp_mehriddin.utils.ConnectInternet
import uz.gita.bookapp_mehriddin.utils.myLog

class DescriptionViewModelImpl(private val repastory: AppRepastory) : DescriptionViewModel,
    ViewModel() {

    val db = MyDatabase.getInstance().getBookDao()

    val circularProgressDrawable = CircularProgressDrawable(App.context)

    init {
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
    }


    override val setBtnDownloadBookBackgraundLiveData = MutableLiveData<Pair<String, Boolean>>()
    override val openReadBookLiveData = MutableLiveData<String>()
    override val toastLiveData = MutableLiveData<String>()


    override fun downLoadBook(context: Context, book: BookData) {
        setBtnDownloadBookBackgraundLiveData.value = Pair("Loading...", false)
        if(ConnectInternet(context)){
            repastory.downloadFile(context, book).onEach {
                it.onSuccess {
                    db.update(
                        BookEntity(
                            book.name,
                            book.author,
                            book.title,
                            book.imgUrl,
                            book.desc,
                            true,
                            book.pages,
                            book.readPage,
                            book.genre
                        )
                    )
                    setBtnDownloadBookBackgraundLiveData.value = Pair("Read", true)
                }
                it.onFailure {
                    setBtnDownloadBookBackgraundLiveData.value = Pair("Download", false)
                    toastLiveData.value = it.message
                }
            }.launchIn(viewModelScope)
        }else{
            setBtnDownloadBookBackgraundLiveData.value = Pair("Download", false)
            toastLiveData.value = "Not Internet"
        }



    }

    override fun isFileExist(context: Context, book: BookData) {
        db.getAllBookDownloaded().forEach {
            if (it.name == book.name) {
                setBtnDownloadBookBackgraundLiveData.value = Pair("Read", true)
                return
            }
        }
        setBtnDownloadBookBackgraundLiveData.value = Pair("Download", false)
    }

    override fun openReadBookScreen(bookName: String) {
        myLog(bookName, "ZZZ")
        openReadBookLiveData.value = bookName
    }

    class Factory(private val repastory: AppRepastory) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DescriptionViewModelImpl(repastory) as T
        }
    }
}