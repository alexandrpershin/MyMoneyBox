package com.example.minimoneybox.persistence

import androidx.room.TypeConverter
import com.example.minimoneybox.model.InvestorProduct
import com.example.minimoneybox.model.UserAccounts
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * This class converts from a unknown type into a known type in terms of database types
 * */
class Converters {

    private val gson = Gson()

    @TypeConverter
    fun getAccountsModelString(model: UserAccounts?): String {
        return gson.toJson(model)
    }

    @TypeConverter
    fun getAccountsModelFromString(json: String): UserAccounts? {
        return gson.fromJson(json, UserAccounts::class.java)
    }

    @TypeConverter
    fun getInvestorProductsString(list: List<InvestorProduct>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun getInvestorProductsFromString(json: String): List<InvestorProduct> {
        val listType = object : TypeToken<List<InvestorProduct>>() {
        }.type
        return Gson().fromJson<List<InvestorProduct>>(json, listType)
    }

}
