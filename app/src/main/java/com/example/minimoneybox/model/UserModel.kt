package com.example.minimoneybox.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class UserModel(
    @PrimaryKey(autoGenerate = false) val id: Long = 1L,
    val userName: String = "",
    var accountModel: UserAccountsModel? = null
)