package com.example.minimoneybox.utils

import org.intellij.lang.annotations.Language

/**
 *  object which contains mock responses. Since there are very few responses it is fine to store
 *  JSONs as strings. Strings can be replaced with JSONs from files with *.json extension.
* */

object MockResponse {

    @Language("JSON")
    const val loginResponse: String =
        "{\n  \"user\": {\n    \"email\": \"test@test.com\",\n    \"firstName\": \"Testino\",\n    \"lastName\": \"Testario\"\n  }\n}"

    @Language("JSON")
    const val investorProductsResponse: String =
        "{\n  \"totalPlanValue\": \"500\",\n  \"productResponses\": [\n    {\n      \"id\": \"21\",\n      \"moneybox\": \"25.1\",\n      \"planValue\": \"250.1\",\n      \"product\": {\n        \"friendlyName\": \"Life time\",\n        \"name\": \"LISA\"\n      }\n    },\n    {\n      \"id\": \"24\",\n      \"moneybox\": \"32.7\",\n      \"planValue\": \"350.1\",\n      \"product\": {\n        \"friendlyName\": \"Stocks\",\n        \"name\": \"ST\"\n      }\n    }\n\n  ]\n}"

    @Language("JSON")
    fun productPaymentResponse(): String {
        return "{\n  \"amount\": \"${amount}\"\n}".also { amount += 10 }
    }

    private var amount = 50
}