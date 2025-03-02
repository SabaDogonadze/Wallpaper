package com.example.wallpaperapp.presentation.bottom_sheet

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.wallpaperapp.databinding.FragmentBottomSheetBinding
import com.example.wallpaperapp.util.SetWallpaperUtil
import com.example.wallpaperapp.util.WallpaperTypes
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment(){
    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!

    var bitmap: Bitmap? = null  // Holds the bitmap image that will be used for setting the wallpaper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        d("homeScreen","shemovida onviewcreated")
        setUp()
    }

    private fun setUp(){
        setWallPapers()
    }

    private fun setWallPapers(){
        bitmap?.let { bmp ->
            val wallpaperUtil = SetWallpaperUtil(requireContext(), bmp)    // If the bitmap is valid, an instance of SetWallpaperUtil is created with the current context and the bitmap.

            binding.tvHomeScreen.setOnClickListener {
                d("homeScreen","dayenda wesit")
                wallpaperUtil.setWallpaper(WallpaperTypes.HOME)
                dismiss()  // dismisses the dialog
            }
            binding.tvLockScreen.setOnClickListener {
                wallpaperUtil.setWallpaper(WallpaperTypes.LOCK)
                dismiss()
            }
            binding.tvBothScreen.setOnClickListener {
                wallpaperUtil.setWallpaper(WallpaperTypes.HOME_LOCK)
                dismiss()
            }
        } ?: run {
            Toast.makeText(requireContext(), "No image provided", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


  /* Instead of using the Navigation Component (Where we don't pass big object like Bitmap )
  *  creating an instance of BottomSheetFragment directly */

    companion object {
        fun newInstance(bmp: Bitmap): BottomSheetFragment {  // This ensures that when the fragment is shown, it has the required image data to work with.
            val fragment = BottomSheetFragment()
            fragment.bitmap = bmp
            return fragment  // The fragment instance is returned, fully initialized with the bitmap.

        }
    }

}