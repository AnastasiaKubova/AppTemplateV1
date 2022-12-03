package com.template.appsettings.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.template.appsettings.data.ThemeType

internal class SettingsPreferenceRepositoryImpl(
    private val context: Context,
): SettingsPreferenceRepository {

    private var sharedPreference: SharedPreferences = context.getSharedPreferences(
        SHARED_THEME_PREFERENCE_NAME,
        Context.MODE_PRIVATE
    )

    override var theme: ThemeType
        get() = ThemeType.values().firstOrNull { it.name == sharedPreference.getString(
            USER_THEME_KEY, "") } ?: ThemeType.DEFAULT
        set(value) {
            sharedPreference.edit {
                putString(USER_THEME_KEY, value.name)
                apply()
            }
        }

    companion object {
        private const val USER_THEME_KEY = "USER_THEME_KEY"
        private const val SHARED_THEME_PREFERENCE_NAME = "SHARED_THEME_PREFERENCE_NAME"
    }
}