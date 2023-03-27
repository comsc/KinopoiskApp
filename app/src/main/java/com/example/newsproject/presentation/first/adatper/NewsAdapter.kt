package com.example.newsproject.presentation.first.adatper

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.ViewSizeResolver
import com.bumptech.glide.Glide
import com.example.newsproject.R
import com.example.newsproject.data.models.Doc
import com.example.newsproject.databinding.CardNewsBinding
import com.example.newsproject.presentation.first.Listener

class NewsAdapter(
    private val listener: Listener
) : ListAdapter<Doc, Holder>(Comparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemBinding =
            CardNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(itemBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position), listener)
    }


}

private object Comparator : DiffUtil.ItemCallback<Doc>() {
    override fun areItemsTheSame(oldItem: Doc, newItem: Doc): Boolean {
        return oldItem.id == newItem.id  //здесь нужно сравнивть по уникальному.id
    }

    override fun areContentsTheSame(oldItem: Doc, newItem: Doc): Boolean {
        return oldItem == newItem
    }

}

class Holder(private val binding: CardNewsBinding) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(item: Doc, listener: Listener) {
        with(binding) {
            movieImageBg.load(item.poster?.previewUrl) {
                ViewSizeResolver(movieImageBg)
            }
            movieImagePoster.load(item.poster?.previewUrl) {
                ViewSizeResolver(movieImagePoster)
            }
//            Glide.with(movieImageBg).load(item.poster?.previewUrl).into(movieImageBg)
//            Glide.with(movieImagePoster).load(item.poster?.previewUrl).into(movieImagePoster)
            movieTitle.text = item.name
            genreMovie.text = item.genres?.map { "${it.name}" }?.take(2).toString()
            rateKp.text = "КП: ${item.rating?.kp}"
            rateImdb.text = "ImDB: ${item.rating?.imdb}"
            timeMovie.text = "${item.movieLength.toString()} минут"
            yearMovie.text = "Год выпуска: ${item.year}"
            itemView.setOnClickListener { listener.onClick(item) }
            favoriteIconOff.setOnClickListener {
                if (item.isFavorite) {
                    listener.deleteFavorite(item)
                } else {
                    listener.addFavorite(item)
                }
            }
            favoriteIconOff.setImageResource(
                if (item.isFavorite) {
                    R.drawable.favorite_on
                } else {
                    R.drawable.favorite_off
                }
            )
        }
    }
}
