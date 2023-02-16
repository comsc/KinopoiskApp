package com.example.newsproject.presentation.first

import com.example.newsproject.data.models.Articles

interface Listener {

    fun onClick(item: Articles)

}