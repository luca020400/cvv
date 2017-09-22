package com.luca020400.cvv

import android.content.Context
import android.preference.PreferenceManager
import com.luca020400.cvv.models.LoginRequest

class TokenStorage {
    private val account_name_pref = "pref_name"
    private val account_pass_pref = "pref_pass"
    private val account_token_pref = "pref_token"
    private val account_expire_date_pref = "pref_expire_date"

    fun SetName(context: Context, value: String) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().putString(account_name_pref, value).apply()
    }

    private fun GetName(context: Context): String {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(account_name_pref, "")
    }

    fun SetPass(context: Context, value: String) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().putString(account_pass_pref, value).apply()
    }

    private fun GetPass(context: Context): String {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(account_pass_pref, "")
    }

    fun SetExpireDate(context: Context, value: Long) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().putLong(account_expire_date_pref, value).apply()
    }

    fun SetToken(context: Context, value: String) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().putString(account_token_pref, value).apply()
    }

    private fun GetExpireDate(context: Context): Long {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getLong(account_expire_date_pref, Long.MIN_VALUE)
    }

    fun IsTokenValid(context: Context): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(account_token_pref, "").isNotEmpty()
    }

    fun GetToken(context: Context): String {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return if (System.currentTimeMillis() > GetExpireDate(context)) {
            val response = APIClient.create(context)
                    .doLogin(LoginRequest(GetPass(context), GetName(context)))
                    .execute()
            when (response.isSuccessful) {
                true -> {
                    val token = response.body()!!.token
                    val expire = response.body()!!.expire

                    SetToken(context, token)
                    SetExpireDate(context, expire.time)

                    token
                }
                false -> prefs.getString(account_token_pref, "") // Last hope
            }
        } else {
            prefs.getString(account_token_pref, "")
        }
    }
}
