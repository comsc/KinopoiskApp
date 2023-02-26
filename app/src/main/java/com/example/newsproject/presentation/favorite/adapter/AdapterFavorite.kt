package com.example.newsproject.presentation.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newsproject.R
import com.example.newsproject.data.models.Article
import com.example.newsproject.databinding.CardNewsBinding
import com.example.newsproject.utils.DateUtils

class AdapterFavorite(
    private val onClickItemListener: (Article) -> Unit,
    private val deleteItemFavorite: (Article) -> Unit
) : ListAdapter<Article, HolderFavorite>(Comparator) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderFavorite {
        val itemBinding =
            CardNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HolderFavorite(itemBinding)
    }

    override fun onBindViewHolder(holder: HolderFavorite, position: Int) {
        holder.bind(getItem(position), onClickItemListener, deleteItemFavorite)
    }

}

private object Comparator : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.title == newItem.title  //здесь нужно сравнивть по уникальному.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

}

class HolderFavorite(private val binding: CardNewsBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: Article,
        onClickItemListener: (Article) -> Unit,
        deleteItemFavorite: (Article) -> Unit
    ) {
        with(binding) {
            newsImage.load(item.urlToImage)
            newsTitle.text = item.title
            newsDesc.text = item.description
            newsDate.text = item.publishedAt?.let { DateUtils.toDefaultDate(it) }
            favoriteImg.setImageResource(R.drawable.favorite_on)
            favoriteImg.setOnClickListener { deleteItemFavorite.invoke(item) }
            itemView.setOnClickListener { onClickItemListener.invoke(item) }
        }
    }
}