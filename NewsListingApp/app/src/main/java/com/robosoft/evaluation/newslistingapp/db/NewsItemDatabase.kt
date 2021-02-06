package com.robosoft.evaluation.newslistingapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.robosoft.evaluation.newslistingapp.db.entities.NewsItem
import com.robosoft.evaluation.newslistingapp.model.NewsArticle

@Database(
    entities = [NewsItem::class],
    version = 1
)
abstract class NewsItemDatabase : RoomDatabase() {

    abstract fun getNewsItemDao(): NewsItemDao

    companion object {
        @Volatile
        private var instance: NewsItemDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: createDb(
                        context
                    )
                        .also { instance = it }
            }

        private fun createDb(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NewsItemDatabase::class.java,
            "NewsItem.db"
        ).build()
    }
}