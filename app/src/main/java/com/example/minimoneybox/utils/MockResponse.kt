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
        "{\n  \"totalPlanValue\": \"500\",\n  \"productResponses\": [\n    {\n      \"id\": \"21\",\n      \"moneybox\": \"25.1\",\n      \"planValue\": \"250.1\",\n      \"product\": {\n        \"friendlyName\": \"Life time\",\n        \"name\": \"LISA\"\n      }\n    },\n    {\n      \"id\": \"24\",\n      \"moneybox\": \"32.7\",\n      \"planValue\": \"350.1\",\n      \"product\": {\n        \"friendlyName\": \"Stocks\",\n        \"name\": \"ST\"\n      }\n    },\n    {\n      \"id\": \"99\",\n      \"moneybox\": \"67.9\",\n      \"planValue\": \"450.1\",\n      \"product\": {\n        \"friendlyName\": \"Mock 1\",\n        \"name\": \"ST\"\n      }\n    },\n    {\n      \"id\": \"199\",\n      \"moneybox\": \"167.9\",\n      \"planValue\": \"257.1\",\n      \"product\": {\n        \"friendlyName\": \"Mock 2\",\n        \"name\": \"ST\"\n      }\n    },\n    {\n      \"id\": \"197\",\n      \"moneybox\": \"197.9\",\n      \"planValue\": \"357.1\",\n      \"product\": {\n        \"friendlyName\": \"Mock 3\",\n        \"name\": \"ST\"\n      }\n    },\n    {\n      \"id\": \"359\",\n      \"moneybox\": \"967.9\",\n      \"planValue\": \"757.1\",\n      \"product\": {\n        \"friendlyName\": \"Mock 4\",\n        \"name\": \"ST\"\n      }\n    }\n  ]\n}"

    @Language("JSON")
    fun productPaymentResponse(): String {
        return "{\n  \"amount\": \"${amount}\"\n}".also { amount += 10 }
    }

    private var amount = 50
}