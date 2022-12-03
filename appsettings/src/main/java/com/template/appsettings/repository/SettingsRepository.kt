package com.template.appsettings.repository

import com.template.appsettings.data.ThemeType

interface SettingsRepository {

    fun getTheme(): ThemeType

    fun updateTheme(theme: ThemeType)

    fun applySavedTheme()
}