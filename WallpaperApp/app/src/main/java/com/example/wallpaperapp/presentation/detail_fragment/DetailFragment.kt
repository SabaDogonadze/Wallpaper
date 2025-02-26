package com.example.wallpaperapp.presentation.detail_fragment

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.wallpaperapp.data.common.Resource
import com.example.wallpaperapp.databinding.FragmentDetailBinding
import com.example.wallpaperapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.jar.Pack200

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    private val detailViewmodel: DetailViewModel by viewModels()
    override fun setUp() {
        val id = DetailFragmentArgs.fromBundle(requireArguments()).imageId
        detailViewmodel.getDetailImage(id)
        observers()
        clickListeners()
    }

    override fun clickListeners() {
        binding.ivFavourite.setOnClickListener {
            it.isSelected = !it.isSelected
            if(it.isSelected){
                Toast.makeText(requireContext(),"Add To Favourites",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(),"Removed From The Favourites",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewmodel.detailImageResponseFlow.collect {
                    when (it) {
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            Glide.with(requireContext())
                                .load(it.dataSuccess?.urls?.imageUrl)
                                .into(binding.ivImage)
                        }

                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                context,
                                "Error: ${it.error}",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        null -> Pack200.Packer.PASS
                    }
                }
            }
        }
    }
}