package com.example.minimoneybox.api.response

import com.example.minimoneybox.model.ProductAccount
import com.example.minimoneybox.model.InvestorProduct
import com.google.gson.annotations.SerializedName

data class InvestorProductsResponse(
    @SerializedName("ProductResponses") val productResponses: List<ProductResponse> = arrayListOf(),
    @SerializedName("TotalPlanValue") val totalPlanValue: Double = 0.0
)

data class ProductResponse(
    @SerializedName("Id") val id: Int = 0,
    @SerializedName("Moneybox") val moneybox: Double = 0.0,
    @SerializedName("PlanValue") val planValue: Double = 0.0,
    @SerializedName("Product") val product: Product = Product()
)

data class Product(
    @SerializedName("FriendlyName") val friendlyName: String = "",
    @SerializedName("Name") val name: String = ""
)

fun InvestorProductsResponse.toInvestorProductModel(): InvestorProduct {
    val products = productResponses
        .map {
            ProductAccount(
                id = it.id,
                planValue = it.planValue,
                moneybox = it.moneybox,
                name = it.product.name,
                friendlyName = it.product.friendlyName
            )
        }

    return InvestorProduct(
        totalPlanValue = totalPlanValue,
        productAccounts = products

    )
}