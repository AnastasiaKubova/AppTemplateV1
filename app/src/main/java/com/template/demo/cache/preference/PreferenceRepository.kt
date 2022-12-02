package com.template.demo.cache.preference

import com.template.appsettings.data.ThemeType

interface PreferenceRepository {

    /**
     * Get application theme.
     */
    fun getTheme(): com.template.appsettings.data.ThemeType

    /**
     * Is user authorized in app.
     */
    fun isAuthorized(): Boolean

    /**
     * Save user email and password.
     */
    fun saveUserData(email: String, password: String)

    /**
     * Get user email and password.
     */
    fun getUserData(): Pair<String, String>

    /**
     * Clear user data.
     */
    fun clearUserData()
}