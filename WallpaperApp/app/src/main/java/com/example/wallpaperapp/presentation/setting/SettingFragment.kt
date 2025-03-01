package com.example.wallpaperapp.presentation.setting


import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.wallpaperapp.databinding.FragmentSettingBinding
import com.example.wallpaperapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {
    private val settingViewModel: SettingViewModel by viewModels()

    override fun setUp() {
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
    }

    private fun openLogInFragment(){
        findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToLoginFragment2())
    }
}