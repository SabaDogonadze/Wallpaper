package com.example.wallpaperapp.presentation.favourite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaperapp.R
import com.example.wallpaperapp.databinding.FavouriteViewholderBinding
import com.example.wallpaperapp.domain.detail.DetailImageModel

class FavouriteRecyclerAdapter : ListAdapter<DetailImageModel, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<DetailImageModel>() {
        override fun areItemsTheSame(
            oldItem: DetailImageModel,
            newItem: DetailImageModel,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DetailImageModel,
            newItem: DetailImageModel,
        ): Boolean {
            return oldItem == newItem
        }
    }
) {
    private var onItemClicked: ((DetailImageModel) -> Unit)? = null

    fun setonItemClickedListener(listener: (DetailImageModel) -> Unit) {
        onItemClicked = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val binding =
            FavouriteViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FavouriteViewHolder) {
            holder.bind(getItem(position))
        }
    }

    inner class FavouriteViewHolder(private val binding: FavouriteViewholderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DetailImageModel) {
            binding.root.setOnClickListener {
                onItemClicked?.invoke(item)
            }
            Glide.with(binding.root.context).load(item.urls.imageUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground).into(binding.ivFavouriteImage)
        }
    }

}