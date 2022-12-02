package com.template.appsettings

import androidx.appcompat.app.AppCompatDelegate
import com.template.appsettings.data.ThemeType

class AppSettingsHandlerImpl: AppSettingsHandler {

    override fun updateTheme(type: ThemeType) {
        AppCompatDelegate.setDefaultNightMode(
            when (type) {
                ThemeType.DEFAULT -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                ThemeType.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                ThemeType.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            }
        )
    }
}