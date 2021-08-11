package com.example.onlineshop.screen.home.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshop.R
import com.example.onlineshop.databinding.ItemCategoriesBinding
import com.example.onlineshop.model.CategoryModel
import com.example.onlineshop.utils.Constants


class CategoryAdapter(
    private val itemListCategory: List<CategoryModel>,
    val itemClick: CategoryAdapterItemClick
) :
    RecyclerView.Adapter<CategoryAdapter.CategoryVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVH {
        val vh =
            LayoutInflater.from(parent.context).inflate(R.layout.item_categories, parent, false)
        return CategoryVH(vh, parent.context)
    }

    override fun onBindViewHolder(holder: CategoryVH, position: Int) {
        holder.bindCategory(itemListCategory[position])

    }

    override fun getItemCount() = itemListCategory.size

    inner class CategoryVH(private val view: View, private val context: Context) :
        RecyclerView.ViewHolder(view) {
        private val binding = ItemCategoriesBinding.bind(view)

        fun bindCategory(item: CategoryModel) {
            view.setOnClickListener {
                itemListCategory.forEach {
                    it.checked = false
                }
                Log.d("item", "bindCategory: ${item.checked}")
                item.checked = true
                notifyDataSetChanged()
                Log.d("itemNext", "bindCategory:${item.checked} ")
                itemClick.onclickItem(item)

            }
            if (item.checked) {
                binding.cardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.purple_700
                    )
                )
                binding.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.white))
            } else {
                binding.cardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.gray
                    )
                )
                binding.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.black))


            }
            binding.tvTitle.text = item.title
            Glide.with(binding.imageItem).load(Constants.OFFER_IMAGE + item.icon)
                .into(binding.imageItem)


        }
    }

}

interface CategoryAdapterItemClick {
    fun onclickItem(item: CategoryModel)
}


