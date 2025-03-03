package com.example.wallpaperapp.presentation.register

import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.wallpaperapp.databinding.FragmentRegisterBinding
import com.example.wallpaperapp.presentation.base.BaseFragment
import com.example.wallpaperapp.presentation.common.ResourceUi
import com.example.wallpaperapp.util.setLocale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.jar.Pack200.Packer.PASS

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val registerViewModel : RegisterViewmodel by viewModels()
    override fun setUp() {
        clickListeners()
        observers()
    }

    override fun clickListeners() {
        binding.btnRegister.setOnClickListener {
            val userPassword = binding.etUserPassword.text.toString()
            val userEmail = binding.etUserEmail.text.toString()
            val repeatPassword = binding.etUserRepeatPassword.text.toString()
            val validationError = registerViewModel.validateUserInputs(userEmail, userPassword,repeatPassword)

            if (validationError != null) {
                Toast.makeText(context, validationError, Toast.LENGTH_LONG).show()
            } else {
                registerViewModel.registerUser(userEmail, password = userPassword)
            }
        }
        binding.btnLanguage.setOnClickListener{
            observeLanguage()
            registerViewModel.toggleLanguage()
        }
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun observers(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                registerViewModel.userRegisterResponseFlow.collect{
                    when(it){
                        is ResourceUi.Success -> {
                            binding.progressBar.visibility = View.GONE
                            findNavController().popBackStack()
                        }
                        is ResourceUi.Error ->  {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(context, it.error, Toast.LENGTH_LONG).show()
                        }
                        is ResourceUi.Loading ->{
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        null -> PASS
                    }
                }
            }
        }
    }

    private fun observeLanguage(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                registerViewModel.languageFlow.collect{language ->
                    setLocale(requireContext(),language)
                    d("shemovida","shemovida")
                    requireActivity().recreate()
                }
            }
        }
    }

    private fun userRegister(email:String,password:String){
        registerViewModel.registerUser(email,password)
    }

}