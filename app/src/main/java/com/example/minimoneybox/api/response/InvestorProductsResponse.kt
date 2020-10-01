package com.example.minimoneybox.api.response

import com.example.minimoneybox.model.InvestorProductModel
import com.example.minimoneybox.model.UserAccountsModel

data class InvestorProductsResponse(
    val MoneyboxEndOfTaxYear: String,
    val ProductResponses: List<ProductResponse>,
    val TotalContributionsNet: Double,
    val TotalEarnings: Double,
    val TotalEarningsAsPercentage: Double,
    val TotalPlanValue: Double
)

data class ProductResponse(
    val CollectionDayMessage: String,
    val Contributions: Contributions,
    val Id: Int,
    val InvestorAccount: InvestorAccount,
    val IsFavourite: Boolean,
    val IsSelected: Boolean,
    val Moneybox: Double,
    val MoneyboxCircle: MoneyboxCircle,
    val Personalisation: Personalisation,
    val PlanValue: Double,
    val Product: Product,
    val State: String,
    val SubscriptionAmount: Double,
    val TotalFees: Double
)

data class Contributions(
    val Status: String
)

data class InvestorAccount(
    val ContributionsNet: Double,
    val EarningsAsPercentage: Double,
    val EarningsNet: Double,
    val TodaysInterest: Double
)

data class MoneyboxCircle(
    val State: String
)

data class Personalisation(
    val HideAccounts: HideAccounts,
    val QuickAddDeposit: QuickAddDeposit
)

data class Product(
    val AnnualLimit: Double,
    val CanWithdraw: Boolean,
    val CategoryType: String,
    val DepositLimit: Double,
    val Documents: Documents,
    val FriendlyName: String,
    val Fund: Fund,
    val Id: Int,
    val InterestRate: String,
    val InterestRateAmount: Double,
    val Lisa: Lisa,
    val LogoUrl: String,
    val MaximumWeeklyDeposit: Double,
    val MinimumWeeklyDeposit: Double,
    val Name: String,
    val ProductHexCode: String,
    val State: String,
    val Type: String
)

data class HideAccounts(
    val Enabled: Boolean,
    val IsHidden: Boolean,
    val Sequence: Int
)

data class QuickAddDeposit(
    val Amount: Double
)

data class Documents(
    val KeyFeaturesUrl: String
)

data class Fund(
    val FundId: Int,
    val LogoUrl: String,
    val Name: String
)

data class Lisa(
    val MaximumBonus: Double
)

fun InvestorProductsResponse.toUserAccountsModel(): UserAccountsModel {
    val products = ProductResponses
        .map {
            InvestorProductModel(
                planValue = it.PlanValue,
                moneybox = it.Moneybox,
                name = it.Product.Name,
                friendlyName = it.Product.FriendlyName
            )
        }

    return UserAccountsModel(
        totalPlanValue = TotalPlanValue,
        products = products

    )
}