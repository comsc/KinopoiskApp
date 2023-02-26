package com.example.newsproject.presentation.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.newsproject.R
import com.example.newsproject.data.models.Article
import com.example.newsproject.databinding.FragmentSecondBinding
import com.example.newsproject.utils.DateUtils

class DetailArticleFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val bundleArgs: DetailArticleFragmentArgs by navArgs()
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setDetail(bundleArgs.article)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            favoriteOffDetail.setOnClickListener {
                viewModel.handleFavorite()
            }
            buttonBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        setupObservers()
    }

    private fun setupObservers() {
        with(viewModel) {
            detailArticle.observe(viewLifecycleOwner) {
                showInfo(it)
            }
        }
    }

    private fun showInfo(article: Article) {
        with(binding) {
            newsDateDetail.text = article.publishedAt?.let { DateUtils.toDefaultDate(it) }
            newsTitleDetail.text = article.title
            newsImageDetail.load(article.urlToImage)
            newsDescDetail.text = article.description
            favoriteOffDetail.setImageResource(
                if (article.isFavorite) {
                    R.drawable.favorite_on
                } else {
                    R.drawable.favorite_off
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
