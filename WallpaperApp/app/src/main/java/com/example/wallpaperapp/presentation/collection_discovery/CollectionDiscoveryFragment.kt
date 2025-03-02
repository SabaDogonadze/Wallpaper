package com.example.wallpaperapp.presentation.collection_discovery

import android.util.Log.d
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpaperapp.data.paging.collection_discovery.CollectionDiscoveryRecyclerAdapter
import com.example.wallpaperapp.databinding.FragmentCollectionDiscoveryBinding
import com.example.wallpaperapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CollectionDiscoveryFragment : BaseFragment<FragmentCollectionDiscoveryBinding>(FragmentCollectionDiscoveryBinding::inflate) {

    private val collectionDiscoveryViewModel: CollectionDiscoveryViewModel by viewModels()
    private lateinit var collectionDiscoveryAdapter: CollectionDiscoveryRecyclerAdapter
    private val args: CollectionDiscoveryFragmentArgs by navArgs()

    override fun setUp() {
        setUpRecycler()
        observers()
        clickListeners()
    }

    override fun clickListeners() {
        collectionDiscoveryAdapter.setonItemClickedListener { item ->
            val action = CollectionDiscoveryFragmentDirections.actionCollectionDiscoveryFragmentToDetailFragment(item.id,item.urls.imageUrl)
            findNavController().navigate(action)
        }
    }

    private fun observers() {
        val collectionId = args.collectionId
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                collectionDiscoveryViewModel.getDiscoveryImages(collectionId).collect { pagingData ->
                    d("shemovida2","$pagingData")
                    collectionDiscoveryAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun setUpRecycler() {
        collectionDiscoveryAdapter = CollectionDiscoveryRecyclerAdapter()
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(),3)
            adapter = collectionDiscoveryAdapter
        }
    }


}