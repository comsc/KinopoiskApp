package com.example.newsproject.presentation.first

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsproject.R
import com.example.newsproject.data.models.Doc
import com.example.newsproject.databinding.FragmentFirstBinding
import com.example.newsproject.presentation.first.adatper.NewsAdapter
import com.example.newsproject.utils.Resource
import com.example.newsproject.utils.extensions.pxInt

class FirstFragment : Fragment() {

    companion object{
        fun newInstance() = FirstFragment()
    }

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val newsListener = object : Listener {
        override fun onClick(item: Doc) {
            navigateToDetailArticle(item)
        }

        override fun addFavorite(item: Doc) {
            showToast("Добавлено в избранное!")
            viewModel.handleFavorites(item)
        }

        override fun deleteFavorite(item: Doc) {
            showToast("Удалено из избранного!")
            viewModel.handleFavorites(item)
        }
    }
    private val adapter: NewsAdapter by lazy { NewsAdapter(newsListener) }
    private val viewModel by viewModels<NewsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        viewModel.articles.observe(viewLifecycleOwner) { response ->
            when (response){
                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    response.data?.let { adapter.submitList(it) }
                }
                is Resource.Error -> {
                    binding.progressBar.isVisible = false
                    response.data?.let {
                        Log.e("checkData","FirstFragment: error: $it")
                    }
                }
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }

        }

        viewModel.favoriteArticles.observe(viewLifecycleOwner) {
            viewModel.getMovieData(it)
        }
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
        if(Resources.getSystem().displayMetrics.widthPixels.pxInt >= 600){
            recycleView.adapter = adapter
            recycleView.layoutManager = GridLayoutManager(context, 2)

        }else {
            recycleView.adapter = adapter
            recycleView.layoutManager = LinearLayoutManager(context)
        }

    }

    private fun navigateToDetailArticle(item: Doc) {
        val bundle = bundleOf("data" to item)
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

