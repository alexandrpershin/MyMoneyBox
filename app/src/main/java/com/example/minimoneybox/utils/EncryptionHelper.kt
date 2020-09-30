package com.example.minimoneybox.utils

import com.scottyab.aescrypt.AESCrypt

object EncryptionHelper {
    fun encryptString(message: String, cryptoKey: String): String {
        val encString = AESCrypt.encrypt(cryptoKey, message)
        return encodeCharset(encString)
    }

    fun decryptString(message: String, cryptoKey: String): String {
        val decString = removeEncoding(message)
        return AESCrypt.decrypt(cryptoKey, decString)
    }

    private fun removeEncoding(value: String?): String {
        var encodedString = value
        encodedString = encodedString!!.replace("x0P1Xx".toRegex(), "\\+").replace("x0P2Xx".toRegex(), "/")
            .replace("x0P3Xx".toRegex(), "=")
        return encodedString
    }

    private fun encodeCharset(value: String): String {
        var encodedString = value
        encodedString = encodedString.replace("\\+".toRegex(), "x0P1Xx").replace("/".toRegex(), "x0P2Xx")
            .replace("=".toRegex(), "x0P3Xx")
        return encodedString
    }
}