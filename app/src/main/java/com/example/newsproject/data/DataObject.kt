package com.example.newsproject.data

import android.content.Context
import com.example.newsproject.data.api.ApiService
import com.example.newsproject.data.db.MainDb
import com.example.newsproject.data.repository.LocalRepository
import com.example.newsproject.data.repository.RemoteRepository

object DataObject {

    lateinit var dataBase: MainDb
    lateinit var remoteRepository: RemoteRepository
    lateinit var localRepository: LocalRepository


    fun initData(context: Context) {
        dataBase = MainDb.getDb(context)
        remoteRepository = RemoteRepository(context)
        localRepository = LocalRepository(dataBase.getDao())
    }
}