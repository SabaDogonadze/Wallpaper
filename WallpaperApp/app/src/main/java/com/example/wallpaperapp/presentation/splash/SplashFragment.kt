package com.example.wallpaperapp.presentation.splash

import android.content.Context
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.wallpaperapp.data.remote.datastore.SessionTracker
import com.example.wallpaperapp.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private val splashViewModel: SplashViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp() // where is write functionality
    }

    private fun setUp(){
        observers()
        splashViewModel.readSession()
    }
    private fun observers(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                SessionTracker.userSession.collect{ session ->
                    d("SaveSession","$session")
                    delay(3000)
                    openFragment(session)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun openFragment(session:Boolean){
        if(session){
            navigateToDiscoveryFragment()
        }else{
            navigateToLoginFragment()
        }
    }
    private fun navigateToLoginFragment(){
        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment2())
    }
    private fun navigateToDiscoveryFragment(){
        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToDiscoveryFragment())
    }
}