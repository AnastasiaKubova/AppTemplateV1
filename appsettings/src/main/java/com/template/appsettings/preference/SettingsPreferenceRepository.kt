package com.template.appsettings.preference

import com.template.appsettings.data.ThemeType

internal interface SettingsPreferenceRepository {

    var theme: ThemeType
}