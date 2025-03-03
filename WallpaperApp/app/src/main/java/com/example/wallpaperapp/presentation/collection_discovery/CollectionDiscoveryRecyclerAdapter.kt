package com.example.wallpaperapp.presentation.collection_discovery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaperapp.databinding.CollectionDiscoveryViewholderBinding

class CollectionDiscoveryRecyclerAdapter: PagingDataAdapter<CollectionDiscoveryImageUi, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<CollectionDiscoveryImageUi>() {
    override fun areItemsTheSame(oldItem: CollectionDiscoveryImageUi, newItem: CollectionDiscoveryImageUi): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CollectionDiscoveryImageUi, newItem: CollectionDiscoveryImageUi): Boolean {
        return oldItem == newItem
    }

}) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CollectionDiscoveryViewHolder) {
            getItem(position)?.let { holder.bind(it) }

        }
    }
    private var onItemClicked: ((CollectionDiscoveryImageUi) -> Unit)? = null

    fun setonItemClickedListener(listener: (CollectionDiscoveryImageUi) -> Unit) {
        onItemClicked = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CollectionDiscoveryViewHolder(
            CollectionDiscoveryViewholderBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        )
    }

    inner class CollectionDiscoveryViewHolder(private val binding: CollectionDiscoveryViewholderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: CollectionDiscoveryImageUi) {
            binding.apply {
                Glide.with(itemView.context).load(image.urls.imageUrl).into(ivCollectionDiscoveryImage)
                root.setOnClickListener {
                    onItemClicked?.invoke(image)
                }
            }
        }
    }
}