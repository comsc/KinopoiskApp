package com.example.newsproject.presentation.first.adatper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newsproject.R
import com.example.newsproject.data.models.Article
import com.example.newsproject.databinding.CardNewsBinding
import com.example.newsproject.presentation.first.Listener
import com.example.newsproject.utils.DateUtils

class NewsAdapter(
    private val listener: Listener
) : ListAdapter<Article, Holder>(Comparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemBinding =
            CardNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(itemBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position), listener)
    }
}

private object Comparator : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.title == newItem.title  //здесь нужно сравнивть по уникальному.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.title == newItem.title && oldItem.isFavorite == newItem.isFavorite
    }

}

class Holder(private val binding: CardNewsBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Article, listener: Listener) {
        with(binding) {
            newsImage.load(item.urlToImage)
            newsTitle.text = item.title
            newsDesc.text = item.description
            newsDate.text = item.publishedAt?.let { DateUtils.toDefaultDate(it) }
            itemView.setOnClickListener { listener.onClick(item) }
            favoriteImg.setOnClickListener {
                if (item.isFavorite) {
                    listener.deleteFavorite(item)
                } else {
                    listener.addFavorite(item)
                }
            }
            favoriteImg.setImageResource(
                if (item.isFavorite) {
                    R.drawable.favorite_on
                } else {
                    R.drawable.favorite_off
                }
            )
        }
    }
}
