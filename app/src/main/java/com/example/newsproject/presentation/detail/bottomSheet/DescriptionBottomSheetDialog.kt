package com.example.newsproject.presentation.detail.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.newsproject.databinding.FragmentBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DescriptionBottomSheetDialog:BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetDialogBinding? = null
    private val binding get() = _binding!!
    private val bundleArgs: DescriptionBottomSheetDialogArgs by navArgs()

    companion object {
        const val TAG = "ModalBottomSheet"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.titleMovieBottom.text = bundleArgs.sheet.name
        binding.fullDescMovie.text = bundleArgs.sheet.description
        binding.rateMovieBottom.text = bundleArgs.sheet.rating?.kp.toString()
        binding.countryMovieBottom.text = bundleArgs.sheet.countries?.get(0)?.name
        binding.genreMovieBottom.text = bundleArgs.sheet.genres?.get(0)?.name
        binding.yearMovieBottom.text = bundleArgs.sheet.year.toString()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}