package com.example.minimoneybox.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.minimoneybox.utils.EncryptionHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * An AES-256 encrypted [SharedPreferences] class, to read and write encrypted preferences.
 */
class EncryptedPreferences(
    val context: Context,
    val cryptoKey: String,
    val preferencesName: String
) {

    private val TAG = EncryptedPreferences::class.java.simpleName
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
    private val encryptedEditor: EncryptedEditor by lazy {
        EncryptedEditor(this)
    }

    private fun containsEncryptedKey(encryptedKey: String): Boolean {
        return sharedPreferences.contains(encryptedKey)
    }

    private fun <T> decryptType(key: String, type: Any, defaultType: T): Any? {
        val encKey = try {
            EncryptionHelper.encryptString(key, cryptoKey)
        } catch (e: Exception) {
            e.printStackTrace()
            return defaultType
        }

        if (!containsEncryptedKey(encKey)) {
            return defaultType
        }

        val value = sharedPreferences.getString(encKey, null)
        if (value.isNullOrEmpty()) {
            return defaultType
        }

        val orgValue = try {
            EncryptionHelper.decryptString(value, cryptoKey)
        } catch (e: Exception) {
            e.printStackTrace()
            return defaultType
        }

        return when (type) {
            is String -> orgValue

            is Int -> try {
                orgValue.toInt()
            } catch (e: NumberFormatException) {
                defaultType
            }

            is Long -> try {
                orgValue.toLong()
            } catch (e: NumberFormatException) {
                defaultType
            }

            is Float -> try {
                orgValue.toFloat()
            } catch (e: NumberFormatException) {
                defaultType
            }

            is Boolean -> orgValue.toBoolean()

            is MutableList<*> -> {
                val listType = object : TypeToken<T>() {}.type
                Gson().fromJson<T>(orgValue, listType)
            }
            else -> defaultType
        }
    }

    /**
     * Retrieve an int value from the preferences.
     *
     * @param key          - The name of the preference to retrieve
     * @param defaultValue - Value to return if this preference does not exist
     * @return int - Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not an
     * int.
     */
    fun getInt(key: String, defaultValue: Int): Int {
        return decryptType(key, 0, defaultValue) as Int
    }

    /**
     * Retrieve a long value from the preferences.
     *
     * @param key          - The name of the preference to retrieve
     * @param defaultValue - Value to return if this preference does not exist
     * @return long - Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a
     * long
     */
    fun getLong(key: String, defaultValue: Long): Long {
        return decryptType(key, 0L, defaultValue) as Long
    }

    /**
     * Retrieve a boolean value from the preferences
     *
     * @param key          - The name of the preference to retrieve
     * @param defaultValue - Value to return if this preference does not exist
     * @return - Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a
     * boolean
     */
    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return decryptType(key, defaultValue, defaultValue) as Boolean
    }

    /**
     * Retrieve a List value from the preferences
     *
     * @param key          - The name of the preference to retrieve
     * @param defaultValue - Value to return if this preference does not exist
     * @return - Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a
     * List
     */

    fun <T> getMutableList(key: String, defaultValue: MutableList<T>): MutableList<T> {
        return decryptType(key, mutableListOf<T>(), defaultValue) as MutableList<T>
    }

    /**
     * Retrieve a float value from the preferences
     *
     * @param key          - The name of the preference to retrieve
     * @param defaultValue - Value to return if this preference does not exist
     * @return float - Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not a
     * float
     */
    fun getFloat(key: String, defaultValue: Float): Float {
        return decryptType(key, 0f, defaultValue) as Float
    }

    /**
     * Retrieve a String value from the preferences
     *
     * @param key          - The name of the preference to retrieve
     * @param defaultValue - Value to return if this preference does not exist
     * @return String - Returns the preference value if it exists, or defValue. Throws ClassCastException if there is a preference with this name that is not
     * a String
     */
    fun getString(key: String, defaultValue: String): String {
        return decryptType(key, "", defaultValue) as String
    }

    /**
     * Checks whether the preferences contains a preference.
     *
     * @param key - The name of the preference to check
     * @return Returns true if the preference exists in the preferences, otherwise false.
     */
    operator fun contains(key: String): Boolean {
        val encKey = EncryptionHelper.encryptString(key, cryptoKey)
        return sharedPreferences.contains(encKey)
    }

    /**
     * Get the Editor for these preferences, through which you can make modifications to the data in the preferences and atomically commit those changes
     * back to
     * the SharedPreferences object.
     *
     * @return [EncryptedEditor]
     */
    fun edit(): EncryptedEditor {
        return encryptedEditor
    }

    class EncryptedEditor internal constructor(private val encryptedPreferences: EncryptedPreferences) {
        private val TAG = EncryptedEditor::class.java.simpleName
        private val editor: SharedPreferences.Editor = encryptedPreferences.sharedPreferences.edit()

        private fun encryptValue(value: String): String {
            return EncryptionHelper.encryptString(value, encryptedPreferences.cryptoKey)
        }

        private fun putValue(key: String, value: String) {
            editor.putString(encryptValue(key), encryptValue(value))
        }

        fun putString(key: String, value: String): EncryptedEditor {
            putValue(key, value)
            return this
        }

        fun putMutableList(key: String, value: MutableList<*>): EncryptedEditor {
            val json = Gson().toJson(value)
            putValue(key, json)
            return this
        }

        fun putInt(key: String, value: Int): EncryptedEditor {
            putValue(key, value.toString())
            return this
        }

        fun putLong(key: String, value: Long): EncryptedEditor {
            putValue(key, value.toString())
            return this
        }

        fun putFloat(key: String, value: Float): EncryptedEditor {
            putValue(key, value.toString())
            return this
        }

        fun putBoolean(key: String, value: Boolean): EncryptedEditor {
            putValue(key, value.toString())
            return this
        }

        fun remove(key: String): EncryptedEditor {
            val encKey = encryptValue(key)
            if (encryptedPreferences.containsEncryptedKey(encKey)) {
                editor.remove(encKey)
            }
            return this
        }

        fun clear(): EncryptedEditor {
            editor.clear()
            return this
        }

        fun apply() {
            editor.apply()
        }

        fun commit(): Boolean {
            return editor.commit()
        }

    }
}