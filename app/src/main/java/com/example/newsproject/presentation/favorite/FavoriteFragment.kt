package com.example.newsproject.presentation.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsproject.R
import com.example.newsproject.data.models.Articles
import com.example.newsproject.databinding.FragmentFavoriteBinding
import com.example.newsproject.presentation.favorite.adapter.AdapterFavorite
import com.example.newsproject.presentation.first.Listener
import com.example.newsproject.presentation.second.DetailViewModel


class FavoriteFragment : Fragment(), Listener {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DetailViewModel>()
    private val adapter:AdapterFavorite by lazy { AdapterFavorite(this) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container,false)
        return binding.root
    }

    companion object{
        fun newInstance() = FavoriteFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcFavorite()
        viewModel.allArticles.observe(viewLifecycleOwner) {
            list ->
            adapter.submitList(list.asReversed())
        }

    }

    private fun initRcFavorite() = with(binding){
        recyclerFavorite.layoutManager = LinearLayoutManager(context)
        recyclerFavorite.adapter = adapter
    }

    override fun onClick(item: Articles) {
        val bundle = bundleOf("article" to item)
        view?.findNavController()?.navigate(R.id.action_mainFragment_to_SecondFragment, bundle)
    }

    override fun adFavoriteOnRc(item: Articles) {
        TODO("Not yet implemented")
    }

    override fun delFavoriteOnRc(item: Articles) {
        viewModel.deleteFavoriteArticle(item)
    }

    override suspend fun boolInTitle(title: String?): Boolean {
        return viewModel.boolTitleFavorite(title)
    }

    override fun showToast() {
        viewModel.showToastContext()
    }

    override fun searchItem(title: String?) {
        viewModel.searchItem(title)
    }

}