package com.example.wallpaperapp.presentation.setting


import android.util.Log.d
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

    private fun observeLanguage(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                settingViewModel.languageFlow.collect{language ->
                    setLocale(requireContext(),language)
                    d("shemovida","shemovida")
                    requireActivity().recreate()
                }
            }
        }
    }

    private fun openLogInFragment(){
        findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToLoginFragment2())
    }
}