package uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.description

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import uz.gita.bookapp_mehriddin.app.App
import uz.gita.bookapp_mehriddin.data.BookData
import uz.gita.bookapp_mehriddin.databinding.ActivityDescriptionBinding
import uz.gita.bookapp_mehriddin.domain.repastory.AppRepastory
import uz.gita.bookapp_mehriddin.domain.usecase.BookUsecase
import uz.gita.bookapp_mehriddin.domain.usecase.BookUsecaseImpl
import uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.description.viewmodel.DescriptionViewModel
import uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.description.viewmodel.impl.DescriptionViewModelImpl
import uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.readbook.ReadBookActivity
import uz.gita.bookapp_mehriddin.utils.checkPermissions2
import uz.gita.bookapp_mehriddin.utils.myLog
import uz.gita.bookapp_mehriddin.utils.showToast
import java.io.File

class DescriptionActivity : AppCompatActivity() {
    private val args by navArgs<DescriptionActivityArgs>()

    private lateinit var binding : ActivityDescriptionBinding
    private val repastory: AppRepastory by lazy { AppRepastory.getInstance() }
    private var bookExist = false
    lateinit var book: BookData
    private val factory: DescriptionViewModelImpl.Factory by lazy {
        DescriptionViewModelImpl.Factory(
            repastory
        )
    }
    private val viewModel: DescriptionViewModel by viewModels<DescriptionViewModelImpl>(
        factoryProducer = { factory })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myLog("Keldi","OPOPP")
        val intent = intent.getParcelableExtra<BookData>("book")
        myLog("Keldi","OPOPP")

        if(intent == null){
            book = args.book
        }else{
            book = intent
        }
        descriptionData(book)

        viewModel.isFileExist(this,book)

        viewModel.setBtnDownloadBookBackgraundLiveData.observe(
            this,
            setBtnDownloadBookBackgraundObserver
        )
        viewModel.openReadBookLiveData.observe(this,openReadBookObserver )
        viewModel.toastLiveData.observe(this,toastLiveDataObserver)


        binding.btnDownload.setOnClickListener {
            myLog(bookExist.toString()+"onClick")
            if(bookExist){
                viewModel.openReadBookScreen(book.name)
            }else {
                checkPermissions2(arrayListOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    viewModel.downLoadBook(this, book)
                }
            }
        }



    }
    private fun descriptionData(book: BookData) {

        val circularProgressDrawable = CircularProgressDrawable(App.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide
            .with(App.context)
            .load(book.imgUrl)
            .centerCrop()
            .placeholder(circularProgressDrawable)
            .into(binding.imgBook)

        binding.txtBookName.text = book.title
        binding.txtBookAuthor.text = book.author
        binding.txtDesc.text = book.desc
    }

    private val setBtnDownloadBookBackgraundObserver = Observer<Pair<String, Boolean>> {

        binding.btnDownload.text = it.first
        bookExist = it.second
    }

    private val openReadBookObserver = Observer<String>{
        val i = Intent(this,ReadBookActivity::class.java)
        i.putExtra("book",book.name)
        startActivity(i)
    }

    private val toastLiveDataObserver = Observer<String> {
        showToast(it)
    }
}