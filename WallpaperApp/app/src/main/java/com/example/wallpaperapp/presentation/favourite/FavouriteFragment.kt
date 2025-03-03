package com.example.wallpaperapp.presentation.favourite

import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaperapp.R
import com.example.wallpaperapp.databinding.FragmentFavouriteBinding
import com.example.wallpaperapp.util.extensions.customize
import com.example.wallpaperapp.presentation.base.BaseFragment
import com.example.wallpaperapp.util.SwipeAndDeleteCallback
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouriteFragment :
    BaseFragment<FragmentFavouriteBinding>(FragmentFavouriteBinding::inflate) {
    private val favouriteViewmodel: FavouriteViewModel by viewModels()
    private lateinit var favouriteAdapter: FavouriteRecyclerAdapter
    override fun setUp() {
        swipeAndDelete()
        setUpRecycler()
        clickListeners()
        observer()
    }

    override fun clickListeners() {
        favouriteAdapter.setonItemClickedListener { image ->
            openDetailsFragment(image.id, image.urls.imageUrl)
        }
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                favouriteViewmodel.favouriteImages.collect { images ->
                    favouriteAdapter.submitList(images)
                    d("favouriteRoomDataBase", "${images}")
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                favouriteViewmodel.favouriteImages.collect { images ->
                    if (images.isEmpty()) {
                        // binding.recyclerview.visibility = View.GONE
                        binding.tvFavouritesEmpty.visibility = View.VISIBLE
                    } else {
                        // binding.recyclerview.visibility = View.VISIBLE
                        binding.tvFavouritesEmpty.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun setUpRecycler() {
        favouriteAdapter = FavouriteRecyclerAdapter()
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favouriteAdapter
        }
    }

    private fun openDetailsFragment(imageId: String, imageUrl: String) {
        findNavController().navigate(
            FavouriteFragmentDirections.actionFavouriteFragmentToDetailFragment(
                imageId,
                imageUrl
            )
        )
    }

    private fun swipeAndDelete() {
        val swipeAndDelete = object : SwipeAndDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                val itemToDelete = favouriteAdapter.currentList[position]
                favouriteViewmodel.removeFavourite(itemToDelete)
                Snackbar.make(requireView(),
                    getString(R.string.favourites_item_deleted), Snackbar.LENGTH_LONG)
                    .customize(
                        ContextCompat.getColor(requireContext(), R.color.red),
                        ContextCompat.getColor(requireContext(), R.color.white)
                    )
                    .show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeAndDelete)
        itemTouchHelper.attachToRecyclerView(binding.recyclerview)
    }
}