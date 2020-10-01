package com.example.minimoneybox.persistence

import androidx.room.TypeConverter
import com.example.minimoneybox.model.UserAccountsModel
import com.example.minimoneybox.model.UserModel
import com.google.gson.Gson


/**
 * This class converts from a unknown type into a known type in terms of database types
* */
class Converters {

    private val gson = Gson()

    @TypeConverter
    fun getStringUser(user: UserModel): String {
        return gson.toJson(user)
    }

    @TypeConverter
    fun getAccountsModelString(model: UserAccountsModel?): String {
        return gson.toJson(model)
    }


    @TypeConverter
    fun getAccountsModelFromString(json : String): UserAccountsModel? {
        return gson.fromJson(json, UserAccountsModel::class.java)
    }



}
