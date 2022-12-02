package com.template.demo.presentation.fragment.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.template.appsettings.AppSettingsHandler
import com.template.demo.R
import com.template.demo.domain.interactor.user.UserInteractor
import com.template.demo.presentation.fragment.base.BaseViewModel
import com.template.demo.presentation.fragment.settings.data.UserDataVO
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val userInteractor: UserInteractor,
    private val appSettingsHandler: AppSettingsHandler
): BaseViewModel() {

    /**
     * Follow that user's is loaded.
     */
    private val userDataInternal: MutableLiveData<UserDataVO> = MutableLiveData()
    val userData: LiveData<UserDataVO> = userDataInternal

    /**
     * Load user data.
     */
    fun loadUserData() {
        viewModelScope.launch {
            runCatching {
                handleLoading(true)
                userInteractor.loadUserData()
            }.onSuccess { data ->
                handleRequestSuccess()
                data?.let {
                    userDataInternal.value = UserDataVO(
                        it.userName,
                        it.email,
                        it.password,
                        it.theme,
                    )
                }
            }.onFailure {
                handleRequestError(it)
            }
        }
    }

    /**
     * Clear user data.
     */
    fun clearUserLoginData() {
        userInteractor.clearData()
    }

    /**
     * Handle changing user name.
     */
    fun handleChangeName(newName: String?) {
        viewModelScope.launch {
            runCatching {
                userInteractor.updateUserData(newName = newName)
            }.onSuccess {
                showMessage(R.string.message_data_successfully_updated)
            }.onFailure {
                showMessage(R.string.message_error_data_failed_updated)
            }
        }
    }

    /**
     * Handle changing user email.
     */
    fun handleChangeEmail(newEmail: String?) {
        viewModelScope.launch {
            runCatching {
                userInteractor.updateUserData(newEmail = newEmail)
            }.onSuccess {
                showMessage(R.string.message_data_successfully_updated)
            }.onFailure {
                showMessage(R.string.message_error_data_failed_updated)
            }
        }
    }

    /**
     * Handle changing user password.
     */
    fun handleChangePassword(newPassword: String?) {
        viewModelScope.launch {
            runCatching {
                userInteractor.updateUserData(newPassword = newPassword)
            }.onSuccess {
                showMessage(R.string.message_data_successfully_updated)
            }.onFailure {
                showMessage(R.string.message_error_data_failed_updated)
            }
        }
    }
}