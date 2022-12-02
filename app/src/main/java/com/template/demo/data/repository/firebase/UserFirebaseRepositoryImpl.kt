package com.template.demo.data.repository.firebase

import com.template.basecomponents.converters.FbConverter
import com.template.universalfirebase.firebase_rdb.FirebaseRDBFacade
import com.template.demo.data.repository.firebase.data.FbUserData
import com.template.universalfirebase.firebase_rdb.IdModel
import com.template.demo.domain.data.UserModel
import com.template.demo.domain.repository.UserFirebaseRepository

class UserFirebaseRepositoryImpl(
    private val convert: FbConverter<FbUserData, UserModel>
): UserFirebaseRepository {

    override suspend fun login(email: String, password: String): Boolean {
        return loadUserData(email, password) != null
    }

    override suspend fun registerUser(email: String, password: String, name: String): Boolean {
        FirebaseRDBFacade.insert(
            FbUserData.TABLE_NAME,
            FbUserData(name, email, password)
        )
        return true
    }

    override suspend fun loadUserData(email: String, password: String): UserModel? {
        return getUser(email, password)?.let { convert.convert(it.id, it.data) }
    }

    override suspend fun updateUserData(
        email: String,
        password: String,
        newEmail: String?,
        newPassword: String?,
        newName: String?,
    ): Boolean {
        val user = getUser(email, password) ?: return false
        FirebaseRDBFacade.insert(
            FbUserData.TABLE_NAME,
            user.id,
            user.data.copy(
                email = newEmail ?: user.data.email,
                name = newName ?: user.data.name,
                password = newPassword ?: user.data.password
            )
        )
        return true
    }

    private suspend fun getUser(email: String, password: String): IdModel<FbUserData>? {
        return getAllUsers().firstOrNull { it.data.password == password && it.data.email == email }
    }

    private suspend fun getAllUsers(): List<IdModel<FbUserData>> {
        return FirebaseRDBFacade.getList(
            FbUserData.TABLE_NAME, FbUserData::class.java
        )
    }
}