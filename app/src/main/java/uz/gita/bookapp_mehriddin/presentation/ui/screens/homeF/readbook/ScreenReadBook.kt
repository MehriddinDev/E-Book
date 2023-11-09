package uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.readbook

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.github.barteksc.pdfviewer.util.FitPolicy
import uz.gita.bookapp_mehriddin.R
import uz.gita.bookapp_mehriddin.databinding.ScreenReadBookBinding
import uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.readbook.viewmodel.ReadBookViewModel
import uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.readbook.viewmodel.impl.ReadBookViewModelImpl
import uz.gita.bookapp_mehriddin.utils.showToast
import java.io.File

/*
class ScreenReadBook : Fragment(R.layout.screen_read_book) {
    private val args by navArgs<ScreenReadBookArgs>()
    private val binding by viewBinding(ScreenReadBookBinding::bind)
    private val viewModel: ReadBookViewModel by viewModels<ReadBookViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookName = args.bookName
        binding.pdfView.setNightMode(true)

        viewModel.findBook(bookName, requireContext())

        viewModel.setPdfViewLiveData.observe(viewLifecycleOwner, setPdfViewObserver)
        viewModel.toastLiveData.observe(viewLifecycleOwner, toastObserver)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private val setPdfViewObserver = Observer<File> {
        binding.pdfView.fromFile(it)
            .enableSwipe(true)
            .defaultPage(viewModel.getPageCount())
            .swipeHorizontal(false)
            .pageSnap(true)
            .autoSpacing(true)
            .pageFling(true)
            .enableDoubletap(true)
            .enableAnnotationRendering(false)
            .scrollHandle(DefaultScrollHandle(requireContext()))
            .onPageChange { page, pageCount ->
                binding.txtTotalPageCount.text = pageCount.toString()
                binding.txtCurrentPageCount.text = (page + 1).toString()
            }
            .enableAntialiasing(true)
            .spacing(10)
            .nightMode(false)
            .pageFitPolicy(FitPolicy.WIDTH)
            .load()
    }

    private val toastObserver = Observer<String> {
        showToast(it)
    }
}*/
