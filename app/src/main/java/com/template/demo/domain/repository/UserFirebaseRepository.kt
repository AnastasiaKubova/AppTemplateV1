package com.template.demo.domain.repository

import com.template.demo.domain.data.UserModel

interface UserFirebaseRepository {

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
     * Get user data.
     */
    suspend fun loadUserData(email: String, password: String): UserModel?

    /**
     * Update user data.
     */
    suspend fun updateUserData(
        email: String,
        password: String,
        newEmail: String? = null,
        newPassword: String? = null,
        newName: String? = null,
        birthday: Long? = null
    ): Boolean
}