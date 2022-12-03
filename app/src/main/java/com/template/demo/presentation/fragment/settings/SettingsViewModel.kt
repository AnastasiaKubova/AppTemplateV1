package com.template.demo.presentation.fragment.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.template.appsettings.data.ThemeType
import com.template.demo.R
import com.template.demo.domain.data.UserModel
import com.template.demo.domain.interactor.user.UserInteractor
import com.template.appsettings.repository.SettingsRepository
import com.template.demo.presentation.fragment.base.BaseViewModel
import com.template.demo.presentation.fragment.settings.data.UserSettingsVO
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val userInteractor: UserInteractor,
    private val settingsInteractor: SettingsRepository,
): BaseViewModel() {

    /**
     * Follow that user's is loaded.
     */
    private val userDataInternal: MutableLiveData<UserSettingsVO> = MutableLiveData()
    val userData: LiveData<UserSettingsVO> = userDataInternal

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
                data?.let { userDataInternal.value = it.toUserDataVO() }
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
        updateUserData(newName = newName)
    }

    /**
     * Handle changing user email.
     */
    fun handleChangeEmail(newEmail: String?) {
        updateUserData(newEmail = newEmail)
    }

    /**
     * Handle changing user password.
     */
    fun handleChangePassword(newPassword: String?) {
        updateUserData(newPassword = newPassword)
    }

    /**
     * Handle changing user birthday.
     */
    fun handleChangeBirthday(birthday: Long) {
        updateUserData(birthday = birthday)
    }

    /**
     * Handle changing user theme.
     */
    fun handleChangeTheme(themeId: Int) {
        settingsInteractor.updateTheme(ThemeType.getByTag(themeId))
    }

    private fun updateUserData(
        newEmail: String? = null,
        newPassword: String? = null,
        newName: String? = null,
        birthday: Long? = null,
    ) {
        viewModelScope.launch {
            runCatching {
                userInteractor.updateUserData(
                    newEmail =  newEmail,
                    newPassword = newPassword,
                    newName = newName,
                    birthday = birthday,
                )
            }.onSuccess {
                showMessage(R.string.message_data_successfully_updated)
                loadUserData()
            }.onFailure {
                showMessage(R.string.message_error_data_failed_updated)
            }
        }
    }

    private fun UserModel.toUserDataVO(): UserSettingsVO {
        return UserSettingsVO(
            userName,
            email,
            password,
            birthday?.timeInMillis,
            settingsInteractor.getTheme(),
        )
    }
}