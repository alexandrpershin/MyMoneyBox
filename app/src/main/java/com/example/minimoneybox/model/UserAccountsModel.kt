package com.example.minimoneybox.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class UserAccountsModel(
    val totalPlanValue: Double = 0.0,
    val products: List<InvestorProductModel> = arrayListOf()
)

@Parcelize
data class InvestorProductModel(
    val id : Int = -1,
    val planValue: Double = 0.0,
    var moneybox: Double = 0.0,
    val name: String = "",
    val friendlyName: String = ""
) : Parcelable
