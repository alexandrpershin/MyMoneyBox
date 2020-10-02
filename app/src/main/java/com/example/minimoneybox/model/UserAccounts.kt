package com.example.minimoneybox.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserAccounts(
    @PrimaryKey(autoGenerate = false) val id: Long = 1L,
    val totalPlanValue: Double = 0.0,
    val products: List<InvestorProduct> = arrayListOf()
)

data class InvestorProduct(
    val id: Int = -1,
    val planValue: Double = 0.0,
    var moneybox: Double = 0.0,
    val name: String = "",
    val friendlyName: String = ""
)
