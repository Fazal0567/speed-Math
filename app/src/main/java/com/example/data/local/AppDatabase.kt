package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.dao.PracticeSessionDao
import com.example.data.local.dao.UserStatsDao
import com.example.data.local.entity.PracticeSession
import com.example.data.local.entity.QuestionResult
import com.example.data.local.entity.UserStats

@Database(
    entities = [
        PracticeSession::class,
        QuestionResult::class,
        UserStats::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun practiceSessionDao(): PracticeSessionDao
    abstract fun userStatsDao(): UserStatsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "speed_math_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
