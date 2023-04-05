package com.example.newsproject.presentation.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsproject.R
import com.example.newsproject.data.models.Doc
import com.example.newsproject.databinding.FragmentFavoriteBinding
import com.example.newsproject.presentation.favorite.adapter.AdapterFavorite

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FavoriteViewModel>()
    private val adapter: AdapterFavorite by lazy {
        AdapterFavorite(
            onClickItemListener = { article ->
                navigateToDetailFragment(article)
            },
            deleteItemFavorite = { article ->
                viewModel.deleteFavorite(article)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRcFavorite()
        setupObservers()
    }

    private fun setupObservers() {
        with(viewModel) {
            favoriteArticles.observe(viewLifecycleOwner) { list ->
                binding.emptyImg.isVisible = list.isNullOrEmpty()
                adapter.submitList(list.asReversed())
            }
        }
    }

    private fun initRcFavorite() = with(binding) {
        recyclerFavorite.layoutManager = LinearLayoutManager(context)
        recyclerFavorite.adapter = adapter
    }

    private fun navigateToDetailFragment(item: Doc) {
        val bundle = bundleOf("data" to item)
        findNavController().navigate(R.id.action_favoriteFragment2_to_DetailMovieFragment, bundle)
    }
}