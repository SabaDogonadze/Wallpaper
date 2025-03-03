package com.example.wallpaperapp.presentation.login

import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.wallpaperapp.data.common.Resource
import com.example.wallpaperapp.databinding.FragmentLoginBinding
import com.example.wallpaperapp.presentation.base.BaseFragment
import com.example.wallpaperapp.presentation.common.ResourceUi
import com.example.wallpaperapp.util.setLocale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.jar.Pack200.Packer.PASS

@AndroidEntryPoint
class LoginFragment () : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate){
    private val loginViewModel : LoginViewModel by viewModels()
    override fun setUp() {
        observers()
        clickListeners()
    }
    override fun clickListeners() {
        binding.btnLogin.setOnClickListener {
            val userPassword = binding.etUserPassword.text.toString()
            val userEmail = binding.etUserEmail.text.toString()
            if(loginViewModel.validateUserInputs(userEmail,userPassword)){
                loginViewModel.saveEmailAndUserSession(userEmail)
                userLogin(email = userEmail, password = userPassword)
            }
        }
        binding.tvRegister.setOnClickListener {
            openRegisterFragment()
        }
        binding.btnLanguage.setOnClickListener{
            observeLanguage()
            loginViewModel.toggleLanguage()
        }
    }

   private fun observers(){
       viewLifecycleOwner.lifecycleScope.launch {
           repeatOnLifecycle(Lifecycle.State.STARTED){
                loginViewModel.userLoginResponseFlow.collect{
                    when(it){
                        is ResourceUi.Success -> {
                            binding.progressBar.visibility = View.GONE
                            d("inDadaStore","Resource Succsess shemovida log inshi")
                            loginViewModel.saveEmailAndUserSession(binding.etUserEmail.text.toString())
                            openDiscoveryFragment()
                        }
                        is ResourceUi.Error ->  {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                context,
                                "Error: ${it.error}",
                                Toast.LENGTH_LONG
                            ).show()
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
                loginViewModel.languageFlow.collect{language ->
                    setLocale(requireContext(),language)
                    d("shemovida","shemovida")
                    requireActivity().recreate()
                }
            }
        }
    }
    private fun userLogin(email:String,password:String){
        loginViewModel.userLogIn(email,password)
    }

    private fun openRegisterFragment(){
        findNavController().navigate(LoginFragmentDirections.actionLoginFragment2ToRegisterFragment())
    }
    private fun openDiscoveryFragment(){
        findNavController().navigate(LoginFragmentDirections.actionLoginFragment2ToDiscoveryFragment())
    }

/*
    private fun setLocale(languageCode: String) {   // Locale is responsible for app language which is changed dynamically at run time
        val locale = Locale(languageCode)    // creating locale object,  Locale IS A Class
        Locale.setDefault(locale)   // updating default Locale with newly created locale

        val config = Configuration(resources.configuration)  // creting a copy of a Configuration object, which holds various settings about app display ( scale, orientation,screen size, and locale)
        config.setLocale(locale)  // updating a configurations locale with a new locale which was created above
        // without this app won't load resources in the desired language

        resources.updateConfiguration(config, resources.displayMetrics)  // without this line UI would not reflect language change immediately
    }*/


}