package uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.home.viewmodel

import androidx.lifecycle.LiveData
import com.denzcoskun.imageslider.models.SlideModel
import uz.gita.bookapp_mehriddin.data.BookData
import uz.gita.bookapp_mehriddin.data.source.local.room.entity.BookEntity

interface HomeViewModel {
    val allBooksLiveData:LiveData<List<BookEntity>>
  //  val allBooksFromRoomLiveData:LiveData<List<BookEntity>>
    val showErorrToastLiveData:LiveData<String>
    val imageSliderLiveData:LiveData<List<SlideModel>>
    val openSearchScreenLiveData:LiveData<Unit>

    fun deleteBookFromRoom(bookData: BookData):Int
    //fun getAllBookFromRoomDownload()
    fun workDao()
    fun openSearchScreen()

    //fun getAllBooks()
}