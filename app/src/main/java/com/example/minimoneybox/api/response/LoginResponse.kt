package com.example.minimoneybox.api.response

import com.example.minimoneybox.model.UserModel
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val actionMessage: ActionMessage,
    val informationMessage: String,
    @SerializedName("Session") val session: Session,
    @SerializedName("User") val user: UserDto
)

data class ActionMessage(
    val actions: List<Action>,
    val message: String,
    val type: String
)

data class Session(
    @SerializedName("BearerToken") val bearerToken: String,
    val expiryInSeconds: Int,
    val externalSessionId: String,
    val sessionExternalId: String
)

data class UserDto(
    val amlAttempts: Int,
    val amlStatus: String,
    val animal: String,
    val canReinstateMandate: Boolean,
    val dateCreated: String,
    val directDebitHasBeenSubmitted: Boolean,
    val directDebitMandateStatus: String,
    val doubleRoundUps: Boolean,
    @SerializedName("Email") val email: String,
    @SerializedName("FirstName") val firstName: String,
    val hasCompletedTutorial: Boolean,
    val hasVerifiedEmail: Boolean,
    val intercomHmac: String,
    val intercomHmacAndroid: String,
    val intercomHmaciOS: String,
    val investmentTotal: Double,
    val investorProduct: String,
    val isPinSet: Boolean,
    val jisaRegistrationStatus: String,
    val jisaRoundUpMode: String,
    @SerializedName("LastName") val lastName: String,
    val lastPayment: Double,
    val mobileNumber: String,
    val moneyboxAmount: Double,
    val moneyboxRegistrationStatus: String,
    val monthlyBoostAmount: Double,
    val monthlyBoostDay: Int,
    val monthlyBoostEnabled: Boolean,
    val previousMoneyboxAmount: Double,
    val referralCode: String,
    val registrationStatus: String,
    val roundUpMode: String,
    val roundUpWholePounds: Boolean,
    val userId: String
)

data class Action(
    val Amount: Double,
    val Animation: String,
    val Label: String,
    val Type: String
)
