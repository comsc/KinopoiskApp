package com.example.newsproject.presentation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.newsproject.R
import com.example.newsproject.data.models.Doc
import com.example.newsproject.databinding.FragmentFirstBinding
import com.example.newsproject.databinding.FragmentSearchBinding
import com.example.newsproject.presentation.first.Listener
import com.example.newsproject.presentation.first.adatper.NewsAdapter
import com.example.newsproject.presentation.search.adapter.SearchAdapter


class SearchFragment : Fragment() {
    private val viewModel by viewModels<SearchViewModel>()
    private val adapter: SearchAdapter by lazy {SearchAdapter(listener)}
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val listener = object : Listener {
        override fun onClick(item: Doc) {
            navigateToDetailArticle(item)
        }

        override fun addFavorite(item: Doc) {
         TODO()
        }

        override fun deleteFavorite(item: Doc) {
           TODO()
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
            adapter.submitList(it)
        }
        binding.searchMovie.addTextChangedListener {
            viewModel.searchMovie(it.toString())
        }
    }

    private fun initRcView() = with(binding) {
        rcSearch.layoutManager = LinearLayoutManager(context)
        rcSearch.adapter = adapter
    }

    private fun navigateToDetailArticle(item: Doc) {
        val bundle = bundleOf("data" to item)
        findNavController().navigate(R.id.action_searchFragment_to_DetailArticleFragment, bundle)
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}