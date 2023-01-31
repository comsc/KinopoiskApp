package com.example.newsproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsproject.databinding.FragmentFirstBinding
import com.example.newsproject.models.Articles

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()




        viewModel.myNewsList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list?.articles)
        }

    }

    private fun initRcView() = with(binding) {
        recycleView.layoutManager = LinearLayoutManager(activity)
        recycleView.adapter = adapter




//        val list = listOf(
//            News("", "Первая новость 1", "Здесь будет описание новости 1"),
//            News("", "Вторая новость 2", "Здесь будет описание новости 2"),
//            News("", "Третья новость 3", "Здесь будет описание новости 3"),
//            News("", "Четвертая новость 4", "Здесь будет описание новости 4")
//        )
//        adapter.submitList(list)

    }

//    private fun updateNews() = with(binding){
//        val viewModel by viewModels<NewsViewModel>()
//        viewModel.getNewsData()
//        viewModel.myNewsList.observe(viewLifecycleOwner){
//                list -> adapter.submitList(list.articles)
//
//        }
//
//    }

    override fun onClick(item: Articles) {
        val bundle = bundleOf("article" to item)
        view?.findNavController()?.navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

