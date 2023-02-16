package com.example.newsproject.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsproject.data.models.Articles

@Database(entities = [Articles::class], version = 2)
abstract class MainDb: RoomDatabase() {
    abstract fun getDao(): Dao

    companion object{
        private var database: MainDb ?= null

        @Synchronized
        fun getDb(context: Context): MainDb {
            return if (database == null) {
                database = Room.databaseBuilder(context.applicationContext, MainDb::class.java, "favorite_news.db").build()
                database as MainDb
            }else { database as MainDb}
        }
    }
}
