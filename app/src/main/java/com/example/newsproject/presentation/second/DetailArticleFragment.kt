package com.example.newsproject.presentation.second


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.ViewSizeResolver
import com.bumptech.glide.Glide
import com.example.newsproject.R
import com.example.newsproject.data.models.Doc
import com.example.newsproject.data.models.movie.Trailer
import com.example.newsproject.databinding.FragmentSecondBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController


class DetailArticleFragment : Fragment(){

    private var _binding: FragmentSecondBinding? = null
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

        viewModel.setDetail(bundleArgs.data)
        viewModel.movieId(bundleArgs.data.id.toString())

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
        val youTubePlayerView: YouTubePlayerView = binding.playerDetail
        lifecycle.addObserver(youTubePlayerView)
        youTubePlayerView.enableAutomaticInitialization = false

        val listener:YouTubePlayerListener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                val defaultPlayerUiController =
                    DefaultPlayerUiController(youTubePlayerView, youTubePlayer)
                defaultPlayerUiController.showFullscreenButton(true)
                youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.rootView)
                viewModel.movies.observe(viewLifecycleOwner){
                    if (it.videos?.trailers?.isNotEmpty() == true){
                    val videoId = it.videos.trailers.let { it1 -> getUrlTrailers(it1) }
                    videoId?.let { youTubePlayer.cueVideo(videoId,0f) }}
                    else binding.playerDetail.isVisible = false
                }
            }
        }

        // Disable iFrame UI
        val options: IFramePlayerOptions = IFramePlayerOptions.Builder().controls(0).build()
        youTubePlayerView.initialize(listener, options)
        setupObservers()
    }

    private fun setupObservers() {
        with(viewModel) {
            detailArticle.observe(viewLifecycleOwner) {
                showInfo(it)
            }
        }
    }
    @SuppressLint("SuspiciousIndentation")
    private fun getUrlTrailers(list:List<Trailer>):String? {
        val url = list.last().url
        return if (url?.contains("embed") == true){
            url.substringAfterLast("embed/")
        } else url?.substringAfterLast("=")
    }
//    fun isLanguageRus(string: String): Boolean {
//        return string.contains("[а-я]".toRegex())
//    }


    @SuppressLint("SetTextI18n")
    private fun showInfo(movie: Doc) {
        with(binding) {
//            context?.let { Glide.with(it).load(movie.poster?.url).into(movieImageBackground) }
//            context?.let { Glide.with(it).load(movie.poster?.url).into(imagePosterDetail) }
            movieImageBackground.load(movie.poster?.previewUrl) {
                ViewSizeResolver(movieImageBackground)
            }
            imagePosterDetail.load(movie.poster?.previewUrl) {
                ViewSizeResolver(imagePosterDetail)
            }
            movieTitle.text = movie.name
            movieDescDetail.text = movie.shortDescription
            movieRatingKp.text = "КП: ${movie.rating?.kp}"
            movieRatingImdb.text = "ImDB: ${movie.rating?.imdb}"


            favoriteOffDetail.setImageResource(
                if (movie.isFavorite) {
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
