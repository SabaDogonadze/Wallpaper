package com.example.wallpaperapp.presentation.detail_fragment

import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.wallpaperapp.data.common.Resource
import com.example.wallpaperapp.databinding.FragmentDetailBinding
import com.example.wallpaperapp.domain.detail.DetailImageModel
import com.example.wallpaperapp.domain.detail.DetailUnsplashURL
import com.example.wallpaperapp.presentation.base.BaseFragment
import com.example.wallpaperapp.presentation.bottom_sheet.BottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.jar.Pack200

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    private val detailViewmodel: DetailViewModel by viewModels()
    override fun setUp() {
        val id = DetailFragmentArgs.fromBundle(requireArguments()).imageId
        detailViewmodel.getDetailImage(id)
        detailViewmodel.checkIfFavorite(id)
        observers()
        clickListeners()
    }

    override fun clickListeners() {
        binding.ivFavourite.setOnClickListener {
            val id = DetailFragmentArgs.fromBundle(requireArguments()).imageId
            val imageUrl = DetailFragmentArgs.fromBundle(requireArguments()).imageUrl
            it.isSelected = !it.isSelected
            if (it.isSelected) {
                detailViewmodel.addFavourite(
                    DetailImageModel(
                        id = id,
                        width = 0,
                        height = 0,
                        urls = DetailUnsplashURL(imageUrl = imageUrl)
                    )
                )
                Toast.makeText(requireContext(), "Added To Favourites", Toast.LENGTH_SHORT).show()
            } else {
                detailViewmodel.removeFavourite(
                    DetailImageModel(
                        id = id,
                        width = 0,
                        height = 0,
                        urls = DetailUnsplashURL(imageUrl = imageUrl)
                    )
                )
                Toast.makeText(requireContext(), "Removed From The Favourites", Toast.LENGTH_SHORT)
                    .show()
            }
        }



        /* so manually creating a instance of BottomSheetFragment, retrieve a image and then checking if this image is BitmapDrawable
        * this is necessary to get .bitmap out of it. after that passing bitmap with factory method newInstance(bitmap) which guarantess that
        * bottomSheetFragment will start with valid bitMap and it wont be a null  */

        /* if i use safeArgs for example and say findNavControler.navigate(Directions.someAction) this will be a problematic
        * because mitMap can be a large Object and it is not good practice to do it*/

        //findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToBottomSheetFragment())
        // parentFragmentManager is an instance of FragmentManager that manages child fragments.It is used to add the BottomSheetFragment to the UI.
        // The show() method comes from the DialogFragment class (which BottomSheetDialogFragment extends).
        // It displays the BottomSheetFragment as a dialog (a pop-up over the current screen).


        binding.btnSetAs.setOnClickListener {
            val drawable = binding.ivImage.drawable
            if (drawable is BitmapDrawable) {
                val bitmap = drawable.bitmap
                BottomSheetFragment.newInstance(bitmap)
                    .show(parentFragmentManager, "BottomSheetFragment")
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
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewmodel.isFavorite.collect { isFav ->
                    binding.ivFavourite.isSelected = isFav // Update UI based on favorite status
                }
            }
        }
    }
}