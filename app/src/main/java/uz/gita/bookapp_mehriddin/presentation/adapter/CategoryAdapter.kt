package uz.gita.bookapp_mehriddin.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.bookapp_mehriddin.R
import uz.gita.bookapp_mehriddin.data.model.CategoryData
import uz.gita.bookapp_mehriddin.databinding.ItemCategoryBinding
import uz.gita.bookapp_mehriddin.utils.myLog

class CategoryAdapter : ListAdapter<CategoryData, CategoryAdapter.MyHolder>(EventDiffUtil) {
    private lateinit var clickCategoryListener: (String) -> Unit
    fun setClickCategoryListener(block: (String) -> Unit) {
        clickCategoryListener = block
    }

    @SuppressLint("ResourceAsColor")
    inner class MyHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val lastClickPos = 0
        init {
            myLog("Ishladi","JJJJJ")
        }
        fun bind() {

            val data = getItem(adapterPosition)
            if (data.checked) {
                binding.root.background =
                    ContextCompat.getDrawable(binding.root.context, R.drawable.bg_category)
                binding.categoryName.setTextColor(Color.WHITE)
            } else {
                var bool1 = true
                currentList.forEach {
                    if(it.checked){
                        bool1 = false
                    }
                }
                if(data.name=="All" && bool1){
                    binding.root.background =
                        ContextCompat.getDrawable(binding.root.context, R.drawable.bg_category)
                    binding.categoryName.setTextColor(Color.WHITE)
                }else{
                    binding.root.background =
                        ContextCompat.getDrawable(binding.root.context, R.drawable.bg_category2)
                    binding.categoryName.setTextColor(Color.parseColor("#605F5B"))
                }

            }
            binding.categoryName.text = data.name
        }


        init {
            binding.root.setOnClickListener {
                currentList.forEach {
                    it.checked = false
                }
                getItem(adapterPosition).checked = true
                notifyItemRangeChanged(0,currentList.size)
                clickCategoryListener.invoke(getItem(adapterPosition).name)
            }
        }

    }

    object EventDiffUtil : DiffUtil.ItemCallback<CategoryData>() {
        override fun areItemsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind()
    }
}