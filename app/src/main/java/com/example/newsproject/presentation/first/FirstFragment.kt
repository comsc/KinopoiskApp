package com.example.newsproject.presentation.first

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.newsproject.R
import com.example.newsproject.databinding.FragmentFirstBinding
import com.example.newsproject.data.models.Articles
import com.example.newsproject.data.models.NewsList
import com.example.newsproject.presentation.favorite.FavoriteFragment
import com.example.newsproject.presentation.first.adatper.NewsAdapter

class FirstFragment : Fragment(), Listener {

    private var _binding: FragmentFirstBinding? = null
    private val adapter: NewsAdapter by lazy { NewsAdapter(this) }
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
    companion object{
        fun newInstance() = FirstFragment()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()

        viewModel.myNewsList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list?.articles)
        }
    }

    private fun initRcView() = with(binding) {
        recycleView.adapter = adapter
    }


    override fun onClick(item: Articles) {
        val bundle = bundleOf("article" to item)
        view?.findNavController()?.navigate(R.id.action_mainFragment_to_SecondFragment, bundle)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

