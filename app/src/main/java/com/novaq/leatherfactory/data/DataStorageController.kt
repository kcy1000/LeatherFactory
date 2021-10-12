package com.novaq.leatherfactory.data

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.novaq.leatherfactory.log.LogManager

/**
 * SharedPreference로 유저데이터를 저장하는 클래스
 */
class DataStorageController private constructor(context: Context) {

    companion object {
        const val KEY_USER_ID = "USER_ID"
        const val KEY_USER_PWD = "PASSWD"
        const val KEY_AUTO_LOGIN = "AUTO_LOGIN"

        private lateinit var instance: DataStorageController

        fun init(context: Context) {
            instance = DataStorageController(context).also { instance = it }
        }

        fun getInstance(): DataStorageController = instance
    }

    private val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

    private val fileName = "auth_data"

    private val sharedPreferences = EncryptedSharedPreferences
            .create(context,
                    fileName,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

    /**
     * 데이터 저장
     * key 값과 value로 저장한다.
     */
    fun saveData(key: String, value: String) {
        try {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(key, value)
            editor.apply()
        } catch (e: NullPointerException) {
            e.message?.let { LogManager.log.e(it) }
        }
    }

    fun saveData(key: String, value: Int) {
        try {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putInt(key, value)
            editor.apply()
        } catch (e: NullPointerException) {
            e.message?.let { LogManager.log.e(it) }
        }
    }

    fun saveData(key:String, value: Boolean = false) {
        try {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean(key, value)
            editor.apply()
        } catch (e: NullPointerException) {
            e.message?.let { LogManager.log.e(it) }
        }
    }

    fun loadData(key: String, defaultValue: String = ""): String {
        var result = ""
        sharedPreferences.getString(key, defaultValue)?.let { result = it }
        return result
    }

    fun loadIntData(key: String, default: Int): Int {
        var result = 0
        sharedPreferences.getInt(key, default).let { result = it }
        return result
    }

    fun loadBooleanData(key: String, default: Boolean = false): Boolean {
        var result = false
        sharedPreferences.getBoolean(key, default).let { result = it }
        return result
    }

    fun setAttribute(key: String, attribute: String) {
        LogManager.log.d("save local : key[$key], attribute[$attribute]")
        saveData(key, attribute)
    }

    fun getAttribute(key: String): String {
        return loadData(key)
    }

    fun clearAll() {
        try {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
        } catch (e: NullPointerException) {
            e.message?.let { LogManager.log.e(it) }
        }
    }


    //============================================================================
    // 여기서부터가 많이 쓰일듯
    //============================================================================
    var isAutoLogin: Boolean
        get() = loadBooleanData(KEY_AUTO_LOGIN)
        set(isAutoLogin) {
            saveData(KEY_AUTO_LOGIN, isAutoLogin)
        }
    var userID: String
        get() = loadData(KEY_USER_ID)
        set(userID) {
            saveData(KEY_USER_ID, userID)
        }
    var userPassword: String
        get() = loadData(KEY_USER_PWD)
        set(userPassword) {
            saveData(KEY_USER_PWD, userPassword)
        }
}