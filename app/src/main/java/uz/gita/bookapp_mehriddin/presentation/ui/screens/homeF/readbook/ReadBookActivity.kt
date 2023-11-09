package uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.readbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.github.barteksc.pdfviewer.util.FitPolicy
import uz.gita.bookapp_mehriddin.databinding.ScreenReadBookBinding
import uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.readbook.viewmodel.ReadBookViewModel
import uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.readbook.viewmodel.impl.ReadBookViewModelImpl
import uz.gita.bookapp_mehriddin.utils.showToast
import java.io.File

class ReadBookActivity : AppCompatActivity() {

    private val viewModel: ReadBookViewModel by viewModels<ReadBookViewModelImpl>()
    private lateinit var binding:ScreenReadBookBinding
    var bookName = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ScreenReadBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bookName = intent.getStringExtra("book")!!

        viewModel.findBook(bookName, this)

        viewModel.setPdfViewLiveData.observe(this, setPdfViewObserver)
        viewModel.toastLiveData.observe(this, toastObserver)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private val setPdfViewObserver = Observer<File> {

        binding.pdfView.fromFile(it)
            .enableSwipe(true)
            .defaultPage((viewModel.getBook(bookName).readPage).toInt())
            .swipeHorizontal(false)
            .pageSnap(true)
            .autoSpacing(true)
            .pageFling(true)
            .enableDoubletap(true)
            .enableAnnotationRendering(false)
            .scrollHandle(DefaultScrollHandle(this))
            .onPageChange { page, pageCount ->
                binding.txtTotalPageCount.text = pageCount.toString()
                binding.txtCurrentPageCount.text = (page + 1).toString()
                viewModel.saveReadedPage(page,bookName)
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
}
