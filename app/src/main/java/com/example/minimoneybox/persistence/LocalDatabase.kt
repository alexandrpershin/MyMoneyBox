package com.example.minimoneybox.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.minimoneybox.model.InvestorProduct
import com.example.minimoneybox.model.User
import com.example.minimoneybox.persistence.dao.InvestorProductDao
import com.example.minimoneybox.persistence.dao.UserDao

@Database(
    entities = [User::class, InvestorProduct::class],
    version = 6,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun investorProductDao(): InvestorProductDao

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

