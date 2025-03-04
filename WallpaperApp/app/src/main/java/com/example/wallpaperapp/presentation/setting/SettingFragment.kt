package com.example.wallpaperapp.presentation.setting


import android.Manifest
import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log.d
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.wallpaperapp.databinding.FragmentSettingBinding
import com.example.wallpaperapp.presentation.base.BaseFragment
import com.example.wallpaperapp.util.setLocale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {
    private val settingViewModel: SettingViewModel by viewModels()

    override fun setUp() {
        observers()
        clickListeners()
    }

    override fun clickListeners() {
        binding.btnLogOut.setOnClickListener {
            lifecycleScope.launch {
                settingViewModel.clearSession()
                delay(1500)
                openLogInFragment()
            }
        }
        binding.btnChangeLanguage.setOnClickListener {
            observeLanguage()
            settingViewModel.toggleLanguage()
        }
    }


  /*  private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            loadLockScreenWallpaper()
        } else {
            Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }*/

    private fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                settingViewModel.favouriteImages.collect { images ->
                    binding.tvFavouriteNumber.text = images.size.toString()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                settingViewModel.emailFlow.collect { email ->
                    binding.tvUserEmail.text = email
                }
            }
        }
    }

    private fun observeLanguage() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                settingViewModel.languageFlow.collect { language ->
                    setLocale(requireContext(), language)
                    d("shemovida", "shemovida")
                    requireActivity().recreate()
                }
            }
        }
    }

   /* private fun checkAndRequestPermission() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(permission)
        } else {
            loadLockScreenWallpaper()
        }
    }*/

/*    @SuppressLint("MissingPermission", "NewApi", "ObsoleteSdkInt")
    private fun loadLockScreenWallpaper() {
        try {
            val wallpaperManager = WallpaperManager.getInstance(requireContext())

            // Get Lock Screen wallpaper (API 24+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val lockScreenDrawable: Drawable? = wallpaperManager.getDrawable(WallpaperManager.FLAG_LOCK)
                binding.ivLockScreen.setImageDrawable(lockScreenDrawable)
            }

            // Get Home Screen wallpaper
            val homeScreenDrawable: Drawable? = wallpaperManager.drawable
            binding.ivHomeScreen.setImageDrawable(homeScreenDrawable)

        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Failed to load wallpapers", Toast.LENGTH_SHORT).show()
        }
    }*/

    private fun openLogInFragment() {
        findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToLoginFragment2())
    }
}