package com.template.appsettings

import com.template.appsettings.data.ThemeType

internal interface AppSettingsHandler {

    fun updateTheme(type: ThemeType)
}