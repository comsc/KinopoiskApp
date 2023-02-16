package com.example.newsproject.presentation.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newsproject.data.models.Articles
import com.example.newsproject.databinding.CardNewsBinding
import com.example.newsproject.utils.DateUtils

class AdapterFavorite: ListAdapter<Articles, HolderFavorite>(Comparator){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderFavorite {
        val itemBinding = CardNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HolderFavorite(itemBinding)
    }

    override fun onBindViewHolder(holder: HolderFavorite, position: Int) {
        holder.bind(getItem(position))
    }

}
private object Comparator : DiffUtil.ItemCallback<Articles>(){
    override fun areItemsTheSame(oldItem: Articles, newItem: Articles): Boolean {
        return oldItem.title == newItem.title  //здесь нужно сравнивть по уникальному.id
    }
    override fun areContentsTheSame(oldItem: Articles, newItem: Articles): Boolean {
        return oldItem == newItem
    }

}
class HolderFavorite(private val binding: CardNewsBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind (item: Articles) = with(binding) {
        newsImage.load(item.urlToImage)
        newsTitle.text = item.title
        newsDesc.text = item.description
        newsDate.text = item.publishedAt?.let { DateUtils.toDefaultDate(it) }

    }

}