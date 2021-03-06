package com.mvvm.fithelperapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.fithelperapp.R
import com.mvvm.fithelperapp.api.FitHelperApi
import com.mvvm.fithelperapp.data.Categories.Category
import com.mvvm.fithelperapp.databinding.ItemRecyclerviewCategoryBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.lang.Exception


class CategoriesRecyclerViewAdapter(private val listener: OnHomeRVClickListener) : ListAdapter<Category, CategoriesRecyclerViewAdapter.CategoriesViewHolder>(DiffCallBack()){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val binding = ItemRecyclerviewCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class CategoriesViewHolder(private val binding: ItemRecyclerviewCategoryBinding) : RecyclerView.ViewHolder(binding.root){


        init {
            binding.apply {
                root.setOnClickListener{
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION){
                        val categoryClicked = getItem(position)
                        val categoryClickedPosition = position
                        listener.onCategoryClick(categoryClicked)
                    }
                }
            }
        }
        fun bind(category: Category){
            binding.apply {
                val url: String = FitHelperApi.BASE_URL + category.thumbpath
                categoryName.text = category.name
                Picasso.get().load(url).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.ic_circle).resize(320,200)
                    .into(categoryThumb, object : Callback {
                        override fun onSuccess() {

                        }

                        override fun onError(e: Exception?) {

                            Picasso.get().load(url).placeholder(R.drawable.ic_circle).resize(320,200)
                                .into(categoryThumb)
                        }
                    })
            }
        }
    }





    interface OnHomeRVClickListener{


        fun onCategoryClick(category: Category)
    }

    class DiffCallBack : DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id  == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem

        }


    }
}