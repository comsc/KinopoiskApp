package com.example.newsproject

import android.app.Application
import com.example.newsproject.data.DataObject
import com.example.newsproject.data.db.MainDb
import com.example.newsproject.data.repository.RemoteRepository
import com.example.newsproject.data.repository.LocalRepository

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        DataObject.initData(this)
    }
}