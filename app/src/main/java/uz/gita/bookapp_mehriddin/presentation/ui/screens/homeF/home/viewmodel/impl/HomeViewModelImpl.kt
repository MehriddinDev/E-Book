package uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.home.viewmodel.impl

import androidx.lifecycle.*
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.bookapp_mehriddin.R
import uz.gita.bookapp_mehriddin.data.BookData
import uz.gita.bookapp_mehriddin.data.source.local.room.database.MyDatabase
import uz.gita.bookapp_mehriddin.data.source.local.room.entity.BookEntity
import uz.gita.bookapp_mehriddin.domain.usecase.BookUsecase
import uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.home.viewmodel.HomeViewModel
import uz.gita.bookapp_mehriddin.utils.myLog

class HomeViewModelImpl(private val usecaseImpl: BookUsecase) : ViewModel(), HomeViewModel {

    override val allBooksLiveData: LiveData<List<BookEntity>>
    get() = dao.getAllBookUnDownloaded()

    //override var allBooksFromRoomLiveData = MutableLiveData<List<BookEntity>>()
    override val showErorrToastLiveData = MutableLiveData<String>()
    override val imageSliderLiveData = MutableLiveData<List<SlideModel>>()
    override val openSearchScreenLiveData = MutableLiveData<Unit>()

    private val dao = MyDatabase.getInstance().getBookDao()

    override fun deleteBookFromRoom(bookData: BookData) :Int{
        myLog("delete = HomeViewModelImpl","MKMM")
        val k = dao.deleteBook(
            BookEntity(
                bookData.name,
                bookData.author,
                bookData.title,
                bookData.imgUrl,
                bookData.desc,
                false,
                bookData.pages,
                0,
                bookData.genre
            )
        )
        myLog("num = $k","LLLLL")
        return k
    }

    override fun workDao() {
       dao.update(dao.getAllBook()[0])
    }

    override fun openSearchScreen() {
        openSearchScreenLiveData.value = Unit
    }

    init {
        val imageList = ArrayList<SlideModel>()
        usecaseImpl.getAllBooks().onEach {
            it.onSuccess {
                imageList.add(SlideModel(R.drawable.img1, ScaleTypes.FIT))
                imageList.add(SlideModel(R.drawable.img2, ScaleTypes.FIT))
                imageList.add(SlideModel(R.drawable.img3, ScaleTypes.FIT))
                imageList.add(SlideModel(R.drawable.img4, ScaleTypes.FIT))
                imageList.add(SlideModel(R.drawable.img5, ScaleTypes.FIT))
                imageList.add(SlideModel(R.drawable.img6, ScaleTypes.FIT))
                imageSliderLiveData.value = imageList
            }
            it.onFailure {
                showErorrToastLiveData.value = it.message.toString()
            }
        }.launchIn(viewModelScope)
    }

    class Factory(private val usecase: BookUsecase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModelImpl(usecase) as T
        }
    }
}