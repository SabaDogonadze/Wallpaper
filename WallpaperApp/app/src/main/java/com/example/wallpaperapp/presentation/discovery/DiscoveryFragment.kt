package com.example.wallpaperapp.presentation.discovery

import android.graphics.Color
import android.util.Log
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpaperapp.data.paging.DiscoveryRecyclerAdapter
import com.example.wallpaperapp.databinding.FragmentDiscoveryBinding
import com.example.wallpaperapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.jar.Pack200.Packer.PASS

@AndroidEntryPoint
class DiscoveryFragment :
    BaseFragment<FragmentDiscoveryBinding>(FragmentDiscoveryBinding::inflate) {
    private val discoveryViewModel: DiscoveryViewModel by viewModels()
    private lateinit var adapter: DiscoveryRecyclerAdapter

    override fun setUp() {
        setUpSearchBarColors()
        clickListeners()
        bindObservers()
        setUpRecycler()
        setUpSearch()
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
     /*   viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                discoveryViewModel.discoveryImageFlow.collect { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                discoveryViewModel.pagingDataFlow.collect { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }*/

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                discoveryViewModel.pagingDataFlow.collect { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }
    }


    private fun setUpSearch() {
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    readQueryFromSearch(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    // User cleared the search view; update the ViewModel with an empty query
                    discoveryViewModel.searchImages("")
                }
                return true
            }
        })
    }

    private fun readQueryFromSearch(query: String) {
        discoveryViewModel.searchImages(query)
        Log.d("SearchView", "Searching for: $query")
    }

    private fun setUpSearchBarColors() {
        val searchEditText =
            binding.searchBar.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchEditText.setTextColor(Color.WHITE)
        searchEditText.setHintTextColor(Color.WHITE)
    }

}