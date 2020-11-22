package com.example.minimoneybox.persistence

import androidx.room.TypeConverter
import com.example.minimoneybox.model.InvestorProduct
import com.example.minimoneybox.model.ProductAccount
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * This class converts from a unknown type into a known type in terms of database types
 * */
class Converters {

    private val gson = Gson()

    @TypeConverter
    fun getAccountsModelString(model: InvestorProduct?): String {
        return gson.toJson(model)
    }

    @TypeConverter
    fun getAccountsModelFromString(json: String): InvestorProduct? {
        return gson.fromJson(json, InvestorProduct::class.java)
    }

    @TypeConverter
    fun getInvestorProductsString(list: List<ProductAccount>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun getInvestorProductsFromString(json: String): List<ProductAccount> {
        val listType = object : TypeToken<List<ProductAccount>>() {
        }.type
        return Gson().fromJson<List<ProductAccount>>(json, listType)
    }

}
