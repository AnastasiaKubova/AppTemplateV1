package com.template.demo.cache.preference

interface PreferenceRepository {

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