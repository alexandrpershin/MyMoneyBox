package com.example.minimoneybox.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InvestorProduct(
    @PrimaryKey(autoGenerate = false) val id: Long = 1L,
    val totalPlanValue: Double = 0.0,
    val productAccounts: List<ProductAccount> = arrayListOf()
)

data class ProductAccount(
    val id: Int = -1,
    val planValue: Double = 0.0,
    var moneybox: Double = 0.0,
    val name: String = "",
    val friendlyName: String = ""
)
