package com.template.demo.domain.data

import java.util.Calendar

/**
 * Model with user data.
 *
 * @property id user's id.
 * @property userName user's name.
 * @property email user's email.
 * @property password user's password.
 * @property birthday user's birthday.
 * @property theme application theme.
 */
data class UserModel(
    val id: String,
    val userName: String,
    val email: String,
    val password: String,
    val birthday: Calendar?,
    val theme: com.template.appsettings.data.ThemeType,
)
