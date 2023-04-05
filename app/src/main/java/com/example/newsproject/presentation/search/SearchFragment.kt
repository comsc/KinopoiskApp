package com.example.newsproject.presentation.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsproject.R
import com.example.newsproject.data.models.Doc
import com.example.newsproject.databinding.FragmentSearchBinding
import com.example.newsproject.presentation.first.Listener
import com.example.newsproject.presentation.search.adapter.SearchAdapter
import com.example.newsproject.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    private val viewModel by viewModels<SearchViewModel>()
    private val adapter: SearchAdapter by lazy {SearchAdapter(listener)}
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val listener = object : Listener {
        override fun onClick(item: Doc) {
            navigateToDetailArticle(item)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        viewModel.movies.observe(viewLifecycleOwner){
                response ->
            when (response){
                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    response.data?.let { adapter.submitList(it) }
                }
                is Resource.Error -> {
                    binding.progressBar.isVisible = false
                    response.data?.let {
                        Log.e("MyTAG","Search: error: $it")
                    }
                }
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }

        }

        binding.searchMovie.doAfterTextChanged {
            lifecycleScope.launch {
                delay(1000)
                viewModel.searchMovie(it.toString())
            }
        }
    }

    private fun initRcView() = with(binding) {
        rcSearch.layoutManager = LinearLayoutManager(context)
        rcSearch.adapter = adapter
    }

    private fun navigateToDetailArticle(item: Doc) {
        val bundle = bundleOf("data" to item)
        findNavController().navigate(R.id.action_searchFragment2_to_DetailMovieFragment, bundle)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}