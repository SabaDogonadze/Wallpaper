package com.example.wallpaperapp.presentation.discovery

import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaperapp.databinding.DiscoveryImageViewholderBinding
import com.example.wallpaperapp.domain.discovery.DiscoveryImageModel

class DiscoveryRecyclerAdapter: PagingDataAdapter<DiscoveryImageUi, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<DiscoveryImageUi>() {
    override fun areItemsTheSame(oldItem: DiscoveryImageUi, newItem: DiscoveryImageUi): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DiscoveryImageUi, newItem: DiscoveryImageUi): Boolean {
        return oldItem == newItem
    }

}) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserDataViewHolder) {
            getItem(position)?.let { holder.bind(it) }

        }
    }
    private var onItemClicked: ((DiscoveryImageUi) -> Unit)? = null

    fun setonItemClickedListener(listener: (DiscoveryImageUi) -> Unit) {
        onItemClicked = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserDataViewHolder(
            DiscoveryImageViewholderBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        )
    }

    inner class UserDataViewHolder(private val binding: DiscoveryImageViewholderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: DiscoveryImageUi) {
            binding.apply {
                d("SearchView","Shemovida image")
                Glide.with(itemView.context).load(image.urls.imageUrl).into(ivDiscoveryImage)
                root.setOnClickListener {
                    onItemClicked?.invoke(image)
                }
            }
        }
    }
}