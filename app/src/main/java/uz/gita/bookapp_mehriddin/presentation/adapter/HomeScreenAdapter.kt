package uz.gita.bookapp_mehriddin.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import uz.gita.bookapp_mehriddin.app.App
import uz.gita.bookapp_mehriddin.data.BookData
import uz.gita.bookapp_mehriddin.databinding.ItemBookBinding

class HomeScreenAdapter : ListAdapter<BookData, HomeScreenAdapter.EventViewHolder>(EventDiffUtil) {
    private var clickBookListener: ((BookData) -> Unit)? = null
    private var clickDeleteBtnListener: ((BookData, ImageView) -> Unit)? = null

    object EventDiffUtil : DiffUtil.ItemCallback<BookData>() {
        override fun areItemsTheSame(oldItem: BookData, newItem: BookData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BookData, newItem: BookData): Boolean {
            return oldItem == newItem
        }

    }

    inner class EventViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickBookListener?.invoke(getItem(adapterPosition))
            }

            binding.btnInfo.setOnClickListener {
                clickDeleteBtnListener?.invoke(getItem(adapterPosition), it as ImageView)
            }
        }

        fun bind() {
            val data = getItem(adapterPosition).apply {
                binding.txtBookName.text = title
                binding.txtBookAuthor.text = author
                if (!download) {
                    binding.btnInfo.visibility = View.GONE
                } else {
                    binding.btnInfo.visibility = View.VISIBLE
                }
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

            val animation = AnimationUtils.loadAnimation(
                binding.root.context,
                androidx.appcompat.R.anim.abc_popup_exit
            )
            binding.root.startAnimation(animation)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EventViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind()
    }

    fun setclickBookListener(block: (BookData) -> Unit) {
        clickBookListener = block
    }

    fun setDeleteBtnListener(block: (BookData, ImageView) -> Unit) {
        clickDeleteBtnListener = block
    }


}