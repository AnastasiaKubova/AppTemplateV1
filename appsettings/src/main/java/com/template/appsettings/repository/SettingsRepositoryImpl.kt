package com.template.appsettings.repository

import com.template.appsettings.AppSettingsHandler
import com.template.appsettings.data.ThemeType
import com.template.appsettings.preference.SettingsPreferenceRepository

internal class SettingsRepositoryImpl(
    private val preferences: SettingsPreferenceRepository,
    private val appSettingsHandler: AppSettingsHandler
): SettingsRepository {

    override fun getTheme(): ThemeType = preferences.theme

    override fun updateTheme(theme: ThemeType) {
        preferences.theme = theme
        appSettingsHandler.updateTheme(theme)
    }

    override fun applySavedTheme() {
        appSettingsHandler.updateTheme(preferences.theme)
    }
}