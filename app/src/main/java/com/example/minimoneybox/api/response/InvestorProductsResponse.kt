package com.example.minimoneybox.api.response

import com.example.minimoneybox.model.InvestorProduct
import com.example.minimoneybox.model.UserAccounts
import com.google.gson.annotations.SerializedName

data class InvestorProductsResponse(
    @SerializedName("ProductResponses")  val productResponses: List<ProductResponse>,
    @SerializedName("TotalPlanValue") val totalPlanValue: Double
)

data class ProductResponse(
    @SerializedName("Id") val id: Int,
    @SerializedName("Moneybox") val moneybox: Double,
    @SerializedName("PlanValue") val planValue: Double,
    @SerializedName("Product") val product: Product
)

data class Product(
    @SerializedName("FriendlyName") val friendlyName: String,
    @SerializedName("Name") val name: String
)

fun InvestorProductsResponse.toUserAccountsModel(): UserAccounts {
    val products = productResponses
        .map {
            InvestorProduct(
                id = it.id,
                planValue = it.planValue,
                moneybox = it.moneybox,
                name = it.product.name,
                friendlyName = it.product.friendlyName
            )
        }

    return UserAccounts(
        totalPlanValue = totalPlanValue,
        products = products

    )
}