package com.example.minimoneybox.persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.minimoneybox.model.User
import com.example.minimoneybox.model.UserAccounts

/**
 * This class allows you to manipulate stored data.
 * */
@Dao
interface UserAccountsDao {

    @Transaction
    fun saveUserAccounts(userAccounts: UserAccounts) {
        deleteUserAccounts()
        userAccounts(userAccounts)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun userAccounts(data: UserAccounts)

    @Query("SELECT * from UserAccounts")
    fun getUserAccounts(): UserAccounts

    @Update
    fun updateUserAccounts(user: UserAccounts)

    @Query("SELECT * from UserAccounts")
    fun getUserAccountsLiveData(): LiveData<UserAccounts>

    @Query("DELETE FROM UserAccounts")
    fun deleteUserAccounts()


}