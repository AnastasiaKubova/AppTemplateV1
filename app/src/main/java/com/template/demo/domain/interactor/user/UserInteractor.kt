package com.template.demo.domain.interactor.user

import com.template.demo.domain.data.UserModel

interface UserInteractor {

    /**
     * Try to login user by email and password.
     *
     * @param email email of user.
     * @param password password of user.
     */
    suspend fun login(email: String, password: String): Boolean

    /**
     * Register user with parameters.
     *
     * @param email email of user.
     * @param password password of user.
     * @param name name of user.
     */
    suspend fun registerUser(
        email: String,
        password: String,
        name: String,
    ): Boolean

    /**
     * Check if user is authorized.
     */
    suspend fun isAuthorized(): Boolean

    /**
     * Get user data.
     */
    suspend fun loadUserData(): UserModel?

    /**
     * Update user data.
     */
    suspend fun updateUserData(
        newEmail: String? = null,
        newPassword: String? = null,
        newName: String? = null,
        birthday: Long? = null
    ): Boolean

    /**
     * Clear user data.
     */
    fun clearData()
}