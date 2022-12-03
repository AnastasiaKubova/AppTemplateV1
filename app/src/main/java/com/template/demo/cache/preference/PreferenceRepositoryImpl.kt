package com.template.demo.cache.preference

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.template.appsettings.data.ThemeType

class PreferenceRepositoryImpl(
    private val context: Context,
): PreferenceRepository {

    private var sharedPreference: SharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE)

    override var theme: ThemeType
        get() = ThemeType.values().firstOrNull { it.name == sharedPreference.getString(USER_THEME_KEY, "") } ?: ThemeType.DEFAULT
        set(value) {
            sharedPreference.modify { putString(USER_THEME_KEY, value.name) }
        }

    override fun isAuthorized(): Boolean {
        val userData = getUserData()
        return userData.first.isNotEmpty() && userData.second.isNotEmpty()
    }

    override fun saveUserData(email: String, password: String) {
        sharedPreference.modify { putString(USER_EMAIL_KEY, email) }
        sharedPreference.modify { putString(USER_PASSWORD_KEY, password) }
    }

    override fun getUserData(): Pair<String, String> {
        return sharedPreference.getString(USER_EMAIL_KEY, "").orEmpty() to sharedPreference.getString(USER_PASSWORD_KEY, "").orEmpty()
    }

    override fun clearUserData() {
        sharedPreference.edit()?.clear()?.apply()
    }

    private fun SharedPreferences.modify(action: SharedPreferences.Editor.() -> Unit) {
        edit().also { it.action() }.apply()
    }

    companion object {
        private const val SHARED_PREFERENCE_NAME = "SHARED_PREFERENCE_NAME"
        private const val USER_EMAIL_KEY = "USER_EMAIL_KEY"
        private const val USER_PASSWORD_KEY = "USER_PASSWORD_KEY"
        private const val USER_THEME_KEY = "USER_THEME_KEY"
    }
}