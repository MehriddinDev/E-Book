package uz.gita.bookapp_mehriddin.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import uz.gita.bookapp_mehriddin.app.App
import uz.gita.bookapp_mehriddin.data.BookData
import uz.gita.bookapp_mehriddin.databinding.ItemBookBinding
import uz.gita.bookapp_mehriddin.databinding.ItemBookCategoryBinding
import uz.gita.bookapp_mehriddin.databinding.ItemCategoryBinding

class BooksAdapter:ListAdapter<BookData,BooksAdapter.MyHolder>(EventDiffUtil) {
    private lateinit var readBtnListener:(BookData)->Unit
    fun setReadBtnListener(block:(BookData)->Unit){
        readBtnListener = block
    }

    inner class MyHolder(private val binding:ItemBookCategoryBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(){
            val data = getItem(adapterPosition).apply {
                binding.txtBookName.text = title
                binding.txtBookAuthor.text = author


                val persent = (readPage * 100)/pages
                binding.progressRead.setProgress(persent.toInt())
                binding.txtReadPersent.text = "$persent%"
            }

            binding.containerReadF.setOnClickListener {
                readBtnListener.invoke(data)
            }

            val circularProgressDrawable = CircularProgressDrawable(App.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            Glide
                .with(App.context)
                .load(data.imgUrl)
                .centerCrop()
                .placeholder(circularProgressDrawable)
                .into(binding.imgBook)
        }
    }

    object EventDiffUtil : DiffUtil.ItemCallback<BookData>() {
        override fun areItemsTheSame(oldItem: BookData, newItem: BookData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BookData, newItem: BookData): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder =
        MyHolder(ItemBookCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
       holder.bind()
    }
}