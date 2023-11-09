package uz.gita.bookapp_mehriddin.presentation.ui.screens.search

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uz.gita.bookapp_mehriddin.R
import uz.gita.bookapp_mehriddin.data.BookData
import uz.gita.bookapp_mehriddin.data.source.local.room.entity.BookEntity
import uz.gita.bookapp_mehriddin.databinding.ActivitySearchBinding
import uz.gita.bookapp_mehriddin.presentation.adapter.HomeScreenAdapter
import uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.description.DescriptionActivity
import uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.readbook.ReadBookActivity
import uz.gita.bookapp_mehriddin.utils.myLog
import uz.gita.bookapp_mehriddin.utils.showToast

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val viewModel: SearchViewModel by viewModels<SearchViewModelImpl>()
    private val adapter by lazy { HomeScreenAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bookList.adapter = adapter
        binding.bookList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter.setclickBookListener {
            val i = Intent(this, DescriptionActivity::class.java)
            i.putExtra("book",it)
            startActivity(i)
            finish()
        }

        adapter.setDeleteBtnListener { bookData, imageView ->
            showPopupMenu(bookData,imageView)
        }


        viewModel.allBooksLiveData.observe(this, allBookLiveDataObserver)
        viewModel.booksByQueryLivedata.observe(this,allBooksObserver)
        binding.btnClose.setOnClickListener {
            finish()
        }

        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.getBookByQuery(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                newText?.let {
                    if (it == "") {
                       viewModel.workDao()
                    } else {
                        viewModel.getBookByQuery(newText)
                    }
                }
                return true
            }
        })
    }

    private val allBookLiveDataObserver = Observer<List<BookEntity>> {
        val books = arrayListOf<BookData>()
        it.forEach { book ->
            books.add(book.toData())
        }
        adapter.submitList(books)
    }

    private fun showPopupMenu(bookData: BookData, view: ImageView) {
        val popUpMenu = PopupMenu(this, view)
        popUpMenu.menuInflater.inflate(R.menu.deete_menu, popUpMenu.menu)
        popUpMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                  val k =   viewModel.deleteBook(bookData.toEntity())
                    myLog("o'chdi = $k","GGG")
                }
                else -> {
                    myLog("o'chdi else","GGG")
                }
            }
            true
        }
        popUpMenu.show()
    }

    private val allBooksObserver = Observer<List<BookEntity>>{
        val books = arrayListOf<BookData>()
        it.forEach { book ->
            books.add(book.toData())
        }
        adapter.submitList(books)
    }
}