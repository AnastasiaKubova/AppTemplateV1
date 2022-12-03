package com.template.appsettings.data

/**
 * Application theme.
 */
enum class ThemeType(val tag: Int) {
    DARK(2),
    LIGHT(1),
    DEFAULT(0);

    companion object {
        fun getByTag(tag: Int): ThemeType {
            return ThemeType.values().firstOrNull { it.tag == tag } ?: ThemeType.DEFAULT
        }
    }
}