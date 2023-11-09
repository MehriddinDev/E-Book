package uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.description.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import uz.gita.bookapp_mehriddin.data.BookData

interface DescriptionViewModel {
    val setBtnDownloadBookBackgraundLiveData:LiveData<Pair<String,Boolean>>
    val openReadBookLiveData:LiveData<String>
    val toastLiveData:LiveData<String>

    fun downLoadBook(context: Context,book: BookData)
    fun isFileExist(context: Context, book: BookData)
    fun openReadBookScreen(bookName:String)
}