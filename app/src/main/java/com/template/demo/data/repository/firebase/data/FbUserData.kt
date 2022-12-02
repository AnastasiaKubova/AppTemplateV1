package com.template.demo.data.repository.firebase.data

data class FbUserData(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val birthday: Long? = null,
) {
    companion object {
        const val TABLE_NAME = "user"
    }
}