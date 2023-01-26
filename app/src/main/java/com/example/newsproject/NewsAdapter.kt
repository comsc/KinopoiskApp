package com.example.newsproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsproject.databinding.CardNewsBinding

class NewsAdapter : ListAdapter<News, NewsAdapter.Holder>(Comparator()) {

    class Holder (view: View) : RecyclerView.ViewHolder(view){
        val binding = CardNewsBinding.bind(view)
        fun bind (item: News) = with(binding) {
            newsTitle.text = item.title
            newsDesc.text = item.description
        }
    }
    class Comparator : DiffUtil.ItemCallback<News>(){
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem  //здесь нужно сравнивть по уникальному.id
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_news, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}
