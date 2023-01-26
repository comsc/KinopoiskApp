package com.example.newsproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsproject.api.Repository
import com.example.newsproject.databinding.FragmentFirstBinding
import retrofit2.Response

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private lateinit var adapter: NewsAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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

    }
    private fun initRcView() = with(binding){
        recycleView.layoutManager = LinearLayoutManager(activity)
 //       val viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        adapter = NewsAdapter()
        recycleView.adapter = adapter
 //       viewModel.getNewsData()


//        val list = listOf(
//            News("", "Первая новость 1", "Здесь будет описание новости 1"),
//            News("", "Вторая новость 2", "Здесь будет описание новости 2"),
//            News("", "Третья новость 3", "Здесь будет описание новости 3"),
//            News("", "Четвертая новость 4", "Здесь будет описание новости 4")
//        )
//        adapter.submitList(list)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}