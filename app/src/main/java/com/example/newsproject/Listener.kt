package com.example.newsproject

import com.example.newsproject.models.Articles

interface Listener {

    fun onClick(item: Articles)
//    var onItemClickListener: ((Articles) -> Unit)?
//    fun onClick(lis: (Articles) -> Unit){
//        onItemClickListener = lis
//    }
}