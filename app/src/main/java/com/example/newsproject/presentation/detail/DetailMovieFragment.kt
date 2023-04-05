package com.example.newsproject.presentation.detail


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.ViewSizeResolver
import com.example.newsproject.R
import com.example.newsproject.data.models.Doc
import com.example.newsproject.databinding.FragmentSecondBinding
import com.example.newsproject.presentation.detail.bottomSheet.DescriptionBottomSheetDialog
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController


class DetailMovieFragment : Fragment(){

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val bundleArgs: DetailMovieFragmentArgs by navArgs()
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

        viewModel.setDetail(bundleArgs.data) // Bundle
        viewModel.movieId(bundleArgs.data.id.toString()) // Get trailers by ID movie
        viewModel.isFavoriteMovie(bundleArgs.data.id) // Check is Favorite ROOM

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Добавлние или удалние из Избранного (ROOM)
        with(binding) {
            favoriteOffDetail.setOnClickListener {
                viewModel.handleFavorite()
                if (viewModel.detailMovie.value?.isFavorite == false) {
                    Toast.makeText(
                        requireContext(),
                        R.string.del_favorite, Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        R.string.added_favorite, Toast.LENGTH_SHORT
                    ).show()
                }
            }
            buttonBack.setOnClickListener {
                findNavController().popBackStack()
            }

            //YouTube Player init
            val youTubePlayerView: YouTubePlayerView = binding.playerDetail
            lifecycle.addObserver(youTubePlayerView)
            youTubePlayerView.enableAutomaticInitialization = false

            val listener: YouTubePlayerListener = object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    val defaultPlayerUiController =
                        DefaultPlayerUiController(youTubePlayerView, youTubePlayer)
                    defaultPlayerUiController.showFullscreenButton(true)
                    youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.rootView)
                    viewModel.movies.observe(viewLifecycleOwner) {
                        if (it.videos?.trailers?.isNotEmpty() == true) {
                            val videoId =
                                it.videos.trailers.let { it1 -> viewModel.getUrlTrailers(it1) }
                            videoId?.let { youTubePlayer.cueVideo(videoId, 0f) }
                            binding.playerDetail.isVisible = true
                        }
                    }
                }
            }


            // Disable iFrame UI
            val options: IFramePlayerOptions = IFramePlayerOptions.Builder().controls(0).build()
            youTubePlayerView.initialize(listener, options)
            setupObservers()

        }
    }
    //
    private fun setupObservers() {
        with(viewModel) {
            detailMovie.observe(viewLifecycleOwner) {
                showInfo(it)
            }
        }
    }



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
            movieDescDetail.text = movie.description
            movieRatingKp.text = "КП: ${movie.rating?.kp}"
            movieRatingImdb.text = "ImDB: ${movie.rating?.imdb}"
            movieDescDetail.setOnClickListener{
                val modalBottomSheet = DescriptionBottomSheetDialog()
                modalBottomSheet.arguments = bundleOf("sheet" to movie)
                parentFragmentManager.let { fManager -> modalBottomSheet.show(fManager, modalBottomSheet.tag) }
            }

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
