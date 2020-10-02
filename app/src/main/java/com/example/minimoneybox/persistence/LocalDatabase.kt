package com.example.minimoneybox.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.minimoneybox.model.User
import com.example.minimoneybox.model.UserAccounts
import com.example.minimoneybox.persistence.dao.UserAccountsDao
import com.example.minimoneybox.persistence.dao.UserDao

@Database(
    entities = [User::class, UserAccounts::class],
    version = 5,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun userAccountsDao(): UserAccountsDao

    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                LocalDatabase::class.java, DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()

        fun destroyInstance() {
            INSTANCE = null
        }


        private const val DATABASE_NAME = "moneybox_db"
    }
}

