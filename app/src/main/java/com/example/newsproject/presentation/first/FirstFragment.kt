package com.example.newsproject.presentation.first

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.newsproject.R
import com.example.newsproject.databinding.FragmentFirstBinding
import com.example.newsproject.data.models.Article
import com.example.newsproject.presentation.first.adatper.NewsAdapter
import com.example.newsproject.presentation.second.DetailViewModel

class FirstFragment : Fragment() {

    companion object{
        fun newInstance() = FirstFragment()
    }

    private var _binding: FragmentFirstBinding? = null
    private val newsListener = object : Listener {
        override fun onClick(item: Article) {
            navigateToDetailArticle(item)
        }

        override fun addFavorite(item: Article) {
            showToast("Добавлено в избранное!")
            viewModel.handleFavorites(item)
        }

        override fun deleteFavorite(item: Article) {
            showToast("Удалено из избранного!")
            viewModel.handleFavorites(item)
        }
    }
    private val adapter: NewsAdapter by lazy { NewsAdapter(newsListener) }

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val binding get() = _binding!!
    private val viewModel by viewModels<NewsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()

        viewModel.articles.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        viewModel.favoriteArticles.observe(viewLifecycleOwner) {
            viewModel.handleAllArticles(it)
        }
    }

    private fun initRcView() = with(binding) {
        recycleView.adapter = adapter
    }

    private fun navigateToDetailArticle(item: Article) {
        val bundle = bundleOf("article" to item)
        findNavController().navigate(R.id.action_mainFragment_to_DetailArticleFragment, bundle)
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

