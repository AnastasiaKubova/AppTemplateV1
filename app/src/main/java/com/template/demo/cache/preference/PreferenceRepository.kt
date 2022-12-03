package com.template.demo.cache.preference

import com.template.appsettings.data.ThemeType

interface PreferenceRepository {

    /**
     * Get or set application theme.
     */
    var theme: ThemeType

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