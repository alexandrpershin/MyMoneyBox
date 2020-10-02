package com.example.minimoneybox.persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.minimoneybox.model.User

/**
 * This class allows you to manipulate stored data.
 * */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("SELECT * from User")
    fun getUser(): User

    @Update
    fun update(user: User)

    @Query("SELECT * from User")
    fun getUserLiveData(): LiveData<User>

    @Query("DELETE FROM User")
    fun deleteUser()
}