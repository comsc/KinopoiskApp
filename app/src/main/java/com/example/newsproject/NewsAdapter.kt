package com.example.newsproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newsproject.databinding.CardNewsBinding
import com.example.newsproject.models.Articles

class NewsAdapter : ListAdapter<Articles, Holder>(Comparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemBinding = CardNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(itemBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}

class Comparator : DiffUtil.ItemCallback<Articles>(){
    override fun areItemsTheSame(oldItem: Articles, newItem: Articles): Boolean {
        return oldItem.title == newItem.title  //здесь нужно сравнивть по уникальному.id
    }
    override fun areContentsTheSame(oldItem: Articles, newItem: Articles): Boolean {
        return oldItem == newItem
    }

}

class Holder(private val binding: CardNewsBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind (item: Articles) = with(binding) {
        newsImage.load(item.urlToImage)
        newsTitle.text = item.title
        newsDesc.text = item.description
    }
}
