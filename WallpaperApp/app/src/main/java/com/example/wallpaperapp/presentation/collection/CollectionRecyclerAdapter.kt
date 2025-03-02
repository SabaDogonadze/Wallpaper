package com.example.wallpaperapp.presentation.collection

import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaperapp.databinding.CollectionItemViewholderBinding
import com.example.wallpaperapp.domain.collection.CollectionModel

class CollectionRecyclerAdapter : PagingDataAdapter<CollectionModel, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<CollectionModel>() {
    override fun areItemsTheSame(oldItem: CollectionModel, newItem: CollectionModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CollectionModel, newItem: CollectionModel): Boolean {
        return oldItem == newItem
    }

}) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CollectionViewHolder) {
            getItem(position)?.let { holder.bind(it) }

        }
    }
    private var onItemClicked: ((CollectionModel) -> Unit)? = null

    fun setonItemClickedListener(listener: (CollectionModel) -> Unit) {
        onItemClicked = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CollectionViewHolder(
            CollectionItemViewholderBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        )
    }

    inner class CollectionViewHolder(private val binding: CollectionItemViewholderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: CollectionModel) {
            binding.apply {
                d("SearchView","Shemovida image")
                Glide.with(itemView.context).load(image.urls.imageUrl).into(ivCollectionImage)
                tvCollectionTitle.text = image.title
                root.setOnClickListener {
                    onItemClicked?.invoke(image)
                }
            }
        }
    }
}

