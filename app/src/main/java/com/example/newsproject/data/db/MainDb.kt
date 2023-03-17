package com.example.newsproject.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsproject.data.models.Doc

@Database(entities = [Doc::class], version = 4, exportSchema = false)
abstract class MainDb : RoomDatabase() {
    abstract fun getDao(): ArticleDao

    companion object {
        @Volatile
        private var database: MainDb? = null

        @Synchronized
        fun getDb(context: Context): MainDb {
            return if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    MainDb::class.java,
                    "favorite_news.db"
                ).fallbackToDestructiveMigration().build()
                database as MainDb
            } else {
                database as MainDb
            }
        }
    }
}
