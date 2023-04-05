package com.example.newsproject.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsproject.databinding.FragmentMainBinding
import com.example.newsproject.presentation.cartoons.CartoonsFragment
import com.example.newsproject.presentation.first.MovieFragment
import com.example.newsproject.presentation.serials.SerialsFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val fragList = listOf(
        MovieFragment.newInstance(),
        SerialsFragment.newInstance(),
        CartoonsFragment.newInstance()
    )
    private val fragtitles = listOf(
        "Фильмы",
        "Сериалы",
        "Мультфильмы"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = binding.viewPager2
        val adapter = ViewPagerAdapter(this, fragList)
        viewPager.adapter = adapter
        TabLayoutMediator(binding.tabsL, binding.viewPager2) { tab, pos ->
            tab.text = fragtitles[pos]
        }.attach()

    }
}