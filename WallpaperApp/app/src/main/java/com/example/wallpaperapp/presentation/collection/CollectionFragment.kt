package com.example.wallpaperapp.presentation.collection

import android.graphics.Color
import android.util.Log
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpaperapp.data.paging.collection.CollectionRecyclerAdapter
import com.example.wallpaperapp.databinding.FragmentCollectionBinding
import com.example.wallpaperapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.jar.Pack200.Packer.PASS

@AndroidEntryPoint
class CollectionFragment : BaseFragment<FragmentCollectionBinding>(FragmentCollectionBinding::inflate) {
    private lateinit var adapter: CollectionRecyclerAdapter
    private val collectionViewModel: CollectionViewModel by viewModels()

    override fun setUp() {
        setUpSearchBarColors()
        bindObservers()
        setUpRecycler()
        setUpSearch()
        clickListeners()
    }

    override fun clickListeners() {
       PASS
    }

    private fun setUpRecycler() {
        adapter = CollectionRecyclerAdapter()
        binding.apply {
            recyclerView.layoutManager = GridLayoutManager(context, 2)
            recyclerView.adapter = adapter
        }
    }

    private fun bindObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                collectionViewModel.pagingDataFlow.collect { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }
    }

    private fun setUpSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    readQueryFromSearch(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    // User cleared the search view; update the ViewModel with an empty query
                    collectionViewModel.searchCollections("")
                }
                return true
            }
        })
    }

    private fun readQueryFromSearch(query: String) {
        collectionViewModel.searchCollections(query)
        Log.d("SearchView", "Searching for: $query")
    }

    private fun setUpSearchBarColors() {
        val searchEditText =
            binding.searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchEditText.setTextColor(Color.WHITE)
        searchEditText.setHintTextColor(Color.WHITE)
    }

}