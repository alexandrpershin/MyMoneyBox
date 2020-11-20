package com.example.minimoneybox.api.response

import com.example.minimoneybox.model.InvestorProduct
import com.example.minimoneybox.model.ProductAccount

data class InvestorProductsResponse(
    val productResponses: List<ProductResponse> = arrayListOf(),
    val totalPlanValue: Double = 0.0
)

data class ProductResponse(
    val id: Int = 0,
    val moneybox: Double = 0.0,
    val planValue: Double = 0.0,
    val product: Product = Product()
)

data class Product(
    val friendlyName: String = "",
    val name: String = ""
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