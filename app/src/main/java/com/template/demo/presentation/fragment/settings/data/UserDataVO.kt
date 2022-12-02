package com.template.demo.presentation.fragment.settings.data

import com.template.appsettings.data.ThemeType

/**
 * Data with information for settings screen.
 */
data class UserDataVO(
    val name: String,
    val email: String,
    val password: String,
    val themeType: com.template.appsettings.data.ThemeType
)