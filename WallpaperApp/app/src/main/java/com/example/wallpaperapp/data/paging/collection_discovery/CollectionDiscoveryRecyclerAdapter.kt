package com.example.wallpaperapp.data.paging.collection_discovery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaperapp.databinding.CollectionDiscoveryViewholderBinding
import com.example.wallpaperapp.domain.collection_discovery.CollectionDiscoveryImage

class CollectionDiscoveryRecyclerAdapter: PagingDataAdapter<CollectionDiscoveryImage, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<CollectionDiscoveryImage>() {
    override fun areItemsTheSame(oldItem: CollectionDiscoveryImage, newItem: CollectionDiscoveryImage): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CollectionDiscoveryImage, newItem: CollectionDiscoveryImage): Boolean {
        return oldItem == newItem
    }

}) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CollectionDiscoveryViewHolder) {
            getItem(position)?.let { holder.bind(it) }

        }
    }
    private var onItemClicked: ((CollectionDiscoveryImage) -> Unit)? = null

    fun setonItemClickedListener(listener: (CollectionDiscoveryImage) -> Unit) {
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
        fun bind(image: CollectionDiscoveryImage) {
            binding.apply {
                Glide.with(itemView.context).load(image.urls.imageUrl).into(ivCollectionDiscoveryImage)
                root.setOnClickListener {
                    onItemClicked?.invoke(image)
                }
            }
        }
    }
}