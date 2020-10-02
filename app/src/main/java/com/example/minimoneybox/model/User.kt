package com.example.minimoneybox.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User(
    @PrimaryKey(autoGenerate = false) val userName: String = ""
)