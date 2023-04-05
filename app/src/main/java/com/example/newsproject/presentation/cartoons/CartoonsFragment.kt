package com.example.newsproject.presentation.cartoons

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.newsproject.R
import com.example.newsproject.data.models.Doc
import com.example.newsproject.databinding.FragmentCartoonsBinding
import com.example.newsproject.presentation.first.Listener
import com.example.newsproject.presentation.first.adatper.MovieAdapter


class CartoonsFragment : Fragment() {
    private var _binding: FragmentCartoonsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<CartoonsViewModel>()
    private val newsListener = object : Listener {
        override fun onClick(item: Doc) {
            navigateToDetailMovie(item)
        }
    }
    private val adapter: MovieAdapter by lazy { MovieAdapter(newsListener) }

    companion object {
        fun newInstance() = CartoonsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartoonsBinding.inflate(inflater, container, false)
        return binding.root}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        adapter.addLoadStateListener { loadState ->
            when (loadState.source.refresh) {
                is LoadState.NotLoading -> {
                    binding.progressBar.isVisible = false
                }
                is LoadState.Loading -> {
                    binding.progressBar.isVisible = true

                }
                is LoadState.Error -> {
                    binding.progressBar.isVisible = false

                }
            }
        }

        viewModel.movie.observe(viewLifecycleOwner){
            adapter.submitData(pagingData = it, lifecycle = lifecycle)
        }
//        viewModel.movie.observe(viewLifecycleOwner) { response ->
//            when (response){
//                is Resource.Success -> {
//                    binding.progressBar.isVisible = false
//                    response.data?.let { adapter.submitList(it) }
//                }
//                is Resource.Error -> {
//                    binding.progressBar.isVisible = false
//                    response.data?.let {
//                        Log.e("checkData","FirstFragment: error: $it")
//                    }
//                }
//                is Resource.Loading -> {
//                    binding.progressBar.isVisible = true
//                }
//            }
//
//        }
//
//        viewModel.favoriteArticles.observe(viewLifecycleOwner) {
//            viewModel.getCartoonsData(it)
//        }
//        val swipeRefreshLayout = binding.itemsSwipeToRefresh
//        swipeRefreshLayout.setOnRefreshListener{
//
//            // Your code goes here
//            // In this code, we are just changing the text in the
//            // textbox
//            viewModel.getNewsData()
//
//            // This line is important as it explicitly refreshes only once
//            // If "true" it implicitly refreshes forever
//            swipeRefreshLayout.isRefreshing = false
//        }
    }

    private fun initRcView() = with(binding) {
        recycleView.adapter = adapter
    }

    private fun navigateToDetailMovie(item: Doc) {
        val bundle = bundleOf("data" to item)
        findNavController().navigate(R.id.action_mainFragment2_to_DetailMovieFragment, bundle)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}