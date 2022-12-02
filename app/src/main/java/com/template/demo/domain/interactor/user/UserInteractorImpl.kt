package com.template.demo.domain.interactor.user

import com.template.demo.cache.preference.PreferenceRepository
import com.template.demo.domain.repository.UserFirebaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserInteractorImpl(
    private val userRepository: UserFirebaseRepository,
    private val preferenceRepository: PreferenceRepository
): UserInteractor {

    override suspend fun login(email: String, password: String) = withContext(Dispatchers.IO) {
        userRepository.login(email, password).also {
            if (it) {
                preferenceRepository.saveUserData(email, password)
            }
        }
    }

    override suspend fun registerUser(email: String, password: String, name: String) = withContext(Dispatchers.IO) {
        userRepository.registerUser(email, password, name).also {
            if (it) {
                preferenceRepository.saveUserData(email, password)
            }
        }
    }

    override suspend fun isAuthorized(): Boolean = withContext(Dispatchers.IO) {
        preferenceRepository.isAuthorized()
    }

    override suspend fun loadUserData() = withContext(Dispatchers.IO) {
        val (email, password) = preferenceRepository.getUserData()
        userRepository.loadUserData(email, password)
    }

    override suspend fun updateUserData(newEmail: String?, newPassword: String?, newName: String?) = withContext(Dispatchers.IO) {
        val (email, password) = preferenceRepository.getUserData()
        userRepository.updateUserData(email, password, newEmail, newPassword, newName)
    }

    override fun clearData() {
        preferenceRepository.clearUserData()
    }
}