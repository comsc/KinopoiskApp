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
import com.example.newsproject.databinding.FragmentSecondBinding
import com.example.newsproject.utils.DateUtils
import kotlinx.coroutines.*

class SecondFragment : Fragment(), CoroutineScope by MainScope() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val bundleArgs: SecondFragmentArgs by navArgs()
    private val viewModel by viewModels<DetailViewModel>()
    //private val exists: Boolean = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val articleArg = bundleArgs.article
        articleArg.let { article ->
            binding.newsImageDetail.load(article.urlToImage)
            binding.newsTitleDetail.text = article.title
            binding.newsDescDetail.text = article.description
            binding.newsDateDetail.text = article.publishedAt?.let { DateUtils.toDefaultDate(it) }
            binding.favoriteOffDetail.setOnClickListener{
                viewModel.saveFavoriteArticle(item = article)
                binding.favoriteOffDetail.setImageResource(R.drawable.favorite_on)
            }
            launch {
               val exists =  viewModel.boolTitleFavorite(title = article.title)
                if (exists) {binding.favoriteOffDetail.setImageResource(R.drawable.favorite_on)
                binding.favoriteOffDetail.setOnClickListener {
                    viewModel.deleteFavoriteArticle(article)
                    binding.favoriteOffDetail.setImageResource(R.drawable.favorite_off)
                }}
                else {binding.favoriteOffDetail.setImageResource(R.drawable.favorite_off)}}
        }




        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
