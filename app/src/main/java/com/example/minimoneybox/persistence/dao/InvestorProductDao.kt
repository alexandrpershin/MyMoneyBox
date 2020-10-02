package com.example.minimoneybox.persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.minimoneybox.model.InvestorProduct

/**
 * This class allows you to manipulate stored data.
 * */
@Dao
interface InvestorProductDao {

    @Transaction
    fun saveInvestorProduct(investorProduct: InvestorProduct) {
        deleteInvestorProduct()
        insertInvestorProduct(investorProduct)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInvestorProduct(data: InvestorProduct)

    @Query("SELECT * from InvestorProduct")
    fun getInvestorProduct(): InvestorProduct


    @Query("SELECT * from InvestorProduct")
    fun getInvestorProductLiveData(): LiveData<InvestorProduct>

    @Query("DELETE FROM InvestorProduct")
    fun deleteInvestorProduct()

}