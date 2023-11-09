package uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.category

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.bookapp_mehriddin.R
import uz.gita.bookapp_mehriddin.data.BookData
import uz.gita.bookapp_mehriddin.data.model.CategoryData
import uz.gita.bookapp_mehriddin.data.source.local.room.entity.BookEntity
import uz.gita.bookapp_mehriddin.databinding.ScreenCategoryBinding
import uz.gita.bookapp_mehriddin.presentation.adapter.BooksAdapter
import uz.gita.bookapp_mehriddin.presentation.adapter.CategoryAdapter
import uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.description.DescriptionActivity
import uz.gita.bookapp_mehriddin.presentation.ui.screens.homeF.readbook.ReadBookActivity
import uz.gita.bookapp_mehriddin.presentation.ui.screens.search.SearchActivity
import uz.gita.bookapp_mehriddin.utils.myLog

class CategoryScreen : Fragment(R.layout.screen_category) {
    private val binding by viewBinding(ScreenCategoryBinding::bind)
    private val viewModel: CategoryViewModel by viewModels<CategoryViewModelImpl>()
    private val booksAdapter by lazy { BooksAdapter() }
    private val categoryAdapter by lazy { CategoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.openDescriptionScreen.observe(this, openReadScreenObserver)
        viewModel.allBookLiveData.observe(this, allBookObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bookList.layoutManager = LinearLayoutManager(requireContext())
        binding.bookList.adapter = booksAdapter

        binding.categoryList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.categoryList.adapter = categoryAdapter

        categoryAdapter.submitList(createCategory())

        booksAdapter.setReadBtnListener {
            myLog("click", "KKJ")
            // viewModel.openReadScreen(requireContext(), it)
            val i = Intent(requireContext(), DescriptionActivity::class.java)
            i.putExtra("book", it)
            startActivity(i)
        }

        categoryAdapter.setClickCategoryListener {
            viewModel.getBooksByCategory(it)
        }

        binding.btnSearch.setOnClickListener {
            val i = Intent(requireContext(), SearchActivity::class.java)
            startActivity(i)
        }


        viewModel.booksByCategory.observe(viewLifecycleOwner,booksByCategoryObserver)
        //viewModel.stateProgressLivedata.observe(viewLifecycleOwner,progressObserver)
    }

    private val allBookObserver = Observer<List<BookEntity>> {
        val books = arrayListOf<BookData>()
        it.forEach { book ->
            books.add(book.toData())
        }
        booksAdapter.submitList(books)
    }

    private val openReadScreenObserver = Observer<String> {
        val i = Intent(requireContext(), ReadBookActivity::class.java)
        i.putExtra("book", it)
        startActivity(i)
    }

    private val progressObserver = Observer<Boolean> {
        if (it) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    fun createCategory(): List<CategoryData> {
        return arrayListOf(
            CategoryData("All",false),CategoryData("Motivation",false), CategoryData("Classic",false), CategoryData("Roman",false),

        )
    }

    private val booksByCategoryObserver = Observer<List<BookEntity>> {
        val books = arrayListOf<BookData>()
        it.forEach { book ->
            books.add(book.toData())
        }
        booksAdapter.submitList(books)
    }

}