package com.template.demo.data.repository.repository

import com.template.appsettings.AppSettingsHandler
import com.template.appsettings.data.ThemeType
import com.template.demo.cache.preference.PreferenceRepository
import com.template.demo.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val preferences: PreferenceRepository,
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