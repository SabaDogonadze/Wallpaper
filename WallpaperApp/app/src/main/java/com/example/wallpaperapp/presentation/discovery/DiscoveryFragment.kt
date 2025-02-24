package com.example.wallpaperapp.presentation.discovery

import android.util.Log.d
import android.view.View
import android.widget.AbsListView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaperapp.data.paging.DiscoveryRecyclerAdapter
import com.example.wallpaperapp.databinding.FragmentDiscoveryBinding
import com.example.wallpaperapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.jar.Pack200.Packer.PASS
import kotlin.math.abs

@AndroidEntryPoint
class DiscoveryFragment :
    BaseFragment<FragmentDiscoveryBinding>(FragmentDiscoveryBinding::inflate) {
    private val discoveryViewModel: DiscoveryViewModel by viewModels()
    private lateinit var adapter: DiscoveryRecyclerAdapter
    override fun setUp() {
        clickListeners()
        bindObservers()
        setUpRecycler()
    }

    override fun clickListeners() {
        PASS
    }

    private fun setUpRecycler() {
        adapter = DiscoveryRecyclerAdapter()
        binding.apply {
            recyclerView.layoutManager = GridLayoutManager(context, 2)
            recyclerView.adapter = adapter
        }
    }

    private fun bindObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                discoveryViewModel.discoveryImageFlow.collect { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }
    }

}