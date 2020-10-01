package com.example.minimoneybox.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.minimoneybox.model.UserModel

/**
 * This class allows you to manipulate stored data.
 * */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userModel: UserModel)

    @Query("SELECT * from UserModel")
    fun getUser(): UserModel

    @Update
    fun update(user: UserModel)

    @Query("SELECT * from UserModel")
    fun getUserLiveData(): LiveData<UserModel>

    @Query("DELETE FROM UserModel")
    fun deleteUser()
}