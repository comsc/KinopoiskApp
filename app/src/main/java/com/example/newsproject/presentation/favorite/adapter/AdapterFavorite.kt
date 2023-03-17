package com.example.newsproject.presentation.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsproject.R
import com.example.newsproject.data.models.Doc
import com.example.newsproject.databinding.CardNewsBinding

class AdapterFavorite(
    private val onClickItemListener: (Doc) -> Unit,
    private val deleteItemFavorite: (Doc) -> Unit
) : ListAdapter<Doc, HolderFavorite>(Comparator) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderFavorite {
        val itemBinding =
            CardNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HolderFavorite(itemBinding)
    }

    override fun onBindViewHolder(holder: HolderFavorite, position: Int) {
        holder.bind(getItem(position), onClickItemListener, deleteItemFavorite)
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

class HolderFavorite(private val binding: CardNewsBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: Doc,
        onClickItemListener: (Doc) -> Unit,
        deleteItemFavorite: (Doc) -> Unit
    ) {
        with(binding) {
            Glide.with(itemView).load(item.poster?.previewUrl).into(newsImage)
            favoriteIconOff.setImageResource(R.drawable.favorite_on)
            favoriteIconOff.setOnClickListener { deleteItemFavorite.invoke(item) }
            itemView.setOnClickListener { onClickItemListener.invoke(item) }
        }
    }
}