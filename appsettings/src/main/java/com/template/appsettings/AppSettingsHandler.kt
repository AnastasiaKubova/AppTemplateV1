package com.template.appsettings

import com.template.appsettings.data.ThemeType

interface AppSettingsHandler {

    fun updateTheme(type: ThemeType)
}