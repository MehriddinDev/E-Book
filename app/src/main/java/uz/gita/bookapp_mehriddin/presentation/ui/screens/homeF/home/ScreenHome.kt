package uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.models.SlideModel
import uz.gita.bookapp_mehriddin.R
import uz.gita.bookapp_mehriddin.data.BookData
import uz.gita.bookapp_mehriddin.data.source.local.room.entity.BookEntity
import uz.gita.bookapp_mehriddin.databinding.ScreenHomeBinding
import uz.gita.bookapp_mehriddin.domain.repastory.AppRepastory
import uz.gita.bookapp_mehriddin.domain.usecase.BookUsecase
import uz.gita.bookapp_mehriddin.domain.usecase.BookUsecaseImpl
import uz.gita.bookapp_mehriddin.presentation.adapter.HomeScreenAdapter
import uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.home.viewmodel.HomeViewModel
import uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.home.viewmodel.impl.HomeViewModelImpl
import uz.gita.bookapp_mehriddin.utils.myLog
import uz.gita.bookapp_mehriddin.utils.showToast

class ScreenHome : Fragment(R.layout.screen_home) {
    private val binding by viewBinding(ScreenHomeBinding::bind)
    private val scope = lifecycleScope
    private var switchState = false
    private val adapter = HomeScreenAdapter()
    private val repastory: AppRepastory by lazy { AppRepastory.getInstance() }
    private val usecase: BookUsecase by lazy { BookUsecaseImpl(repastory) }
    private val factory: HomeViewModelImpl.Factory by lazy { HomeViewModelImpl.Factory(usecase) }
    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>(factoryProducer = { factory })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openSearchScreenLiveData.observe(this,openSearchViewObserber)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
myLog("keldi","UIO")
        binding.rvList.adapter = adapter
        binding.rvList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.allBooksLiveData.observe(viewLifecycleOwner, allBooksObserver)
        viewModel.showErorrToastLiveData.observe(viewLifecycleOwner, showErrorToastObserver)
        viewModel.imageSliderLiveData.observe(viewLifecycleOwner, imageSliderObserver)
       // viewModel.allBooksFromRoomLiveData.observe(viewLifecycleOwner, allBookFromRoomObserver)


        adapter.setclickBookListener {
            findNavController().navigate(
                ScreenHomeDirections.actionScreenHomeToDescriptionActivity(
                    it
                )
            )
            // findNavController().navigate(ScreenHomeDirections.actionScreenHomeToScreenDescription(it))
        }

        adapter.setDeleteBtnListener { _book, _view ->
            showPopupMenu(_book, _view)
        }

        binding.btnSearch.setOnClickListener {
            viewModel.openSearchScreen()
        }

        binding.switchBtn.setOnCheckedChangeListener { buttonView, isChecked ->
            switchState = isChecked
            if (isChecked) {
                myLog("Kirdi","LKJ")
               // viewModel.getAllBookFromRoomDownload()
                viewModel.workDao()
            } else {
                viewModel.workDao()
            }
        }

        if (!switchState){

           // viewModel.getAllBookFromRoomUnDownload()
        }

    }


    private val showErrorToastObserver = Observer<String> {
        showToast(it)
    }

    private val imageSliderObserver = Observer<List<SlideModel>> {
        binding.lastReadList.setImageList(it)
        binding.lastReadList.setSlideAnimation(AnimationTypes.DEPTH_SLIDE)
    }


    private fun showPopupMenu(bookData: BookData, view: ImageView) {
        val popUpMenu = PopupMenu(requireContext(), view)
        popUpMenu.menuInflater.inflate(R.menu.deete_menu, popUpMenu.menu)
        popUpMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                  viewModel.deleteBookFromRoom(bookData)
                }
                else -> {

                }
            }
            true
        }
        popUpMenu.show()
    }

    @SuppressLint("SuspiciousIndentation")
    private val allBookFromRoomObserver = Observer<List< BookEntity>> {
        val books = arrayListOf<BookData>()
        it.forEach { bookEntity ->
            if(bookEntity.download)
            books.add(bookEntity.toData())
        }
        adapter.submitList(books)
    }

    private val allBooksObserver = Observer<List< BookEntity>> {
        val list = ArrayList<BookData>()
        it.forEach {bookEntity->
            if (switchState){
                if(bookEntity.download){
                    list.add(bookEntity.toData())
                }
            }else{
                list.add(bookEntity.toData())
            }

        }
        adapter.submitList(list)
    }

    private val openSearchViewObserber = Observer<Unit>{
        findNavController().navigate(R.id.action_screenHome_to_searchActivity)
    }

}