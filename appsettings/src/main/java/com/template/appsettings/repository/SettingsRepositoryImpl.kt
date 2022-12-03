package com.template.appsettings.repository

import androidx.appcompat.app.AppCompatDelegate
import com.template.appsettings.data.ThemeType
import com.template.appsettings.preference.SettingsPreferenceRepository

internal class SettingsRepositoryImpl(
    private val preferences: SettingsPreferenceRepository,
): SettingsRepository {

    override fun getTheme(): ThemeType = preferences.theme

    override fun updateTheme(theme: ThemeType) {
        preferences.theme = theme
        setTheme(theme)
    }

    override fun applySavedTheme() {
        setTheme(preferences.theme)
    }

    private fun setTheme(type: ThemeType) {
        AppCompatDelegate.setDefaultNightMode(
            when (type) {
                ThemeType.DEFAULT -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                ThemeType.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                ThemeType.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            }
        )
    }
}