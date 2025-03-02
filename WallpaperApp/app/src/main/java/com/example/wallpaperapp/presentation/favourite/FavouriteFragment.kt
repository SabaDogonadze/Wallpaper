package com.example.wallpaperapp.presentation.favourite

import android.util.Log.d
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wallpaperapp.databinding.FragmentFavouriteBinding
import com.example.wallpaperapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouriteFragment : BaseFragment<FragmentFavouriteBinding>(FragmentFavouriteBinding::inflate) {
    private val favouriteViewmodel:FavouriteViewModel by viewModels()
    private lateinit var favouriteAdapter:FavouriteRecyclerAdapter
    override fun setUp() {
        setUpRecycler()
        clickListeners()
        observer()
    }

    override fun clickListeners() {
       favouriteAdapter.setonItemClickedListener { image ->
           favouriteViewmodel.removeFavourite(image)
       }
    }

    private fun observer(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                favouriteViewmodel.favouriteImages.collect{ images ->
                    favouriteAdapter.submitList(images)
                    d("favouriteRoomDataBase","${images}")
                }
            }
        }
    }

    private fun setUpRecycler(){
        favouriteAdapter = FavouriteRecyclerAdapter()
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favouriteAdapter
        }
    }
}