package com.template.demo.presentation.fragment.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.template.demo.R
import com.template.demo.domain.interactor.user.UserInteractor
import com.template.demo.presentation.fragment.base.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userInteractor: UserInteractor
): BaseViewModel() {

    /**
     * Follow that user is authorized in app or not.
     */
    private val isAuthorizedUserInternal: MutableLiveData<Boolean?> = MutableLiveData(null)
    val isAuthorizedUser: LiveData<Boolean?> = isAuthorizedUserInternal

    /**
     * Try to login user in app.
     */
    fun tryLoginUser(email: String, password: String) {
        viewModelScope.launch {
            runCatching {
                handleLoading(true)
                userInteractor.login(email, password)
            }.onSuccess {
                handleRequestSuccess()
                isAuthorizedUserInternal.value = it
            }.onFailure {
                handleRequestError(it)
            }
        }
    }

    /**
     * Check is user authorized in app.
     */
    fun isUserAuthorized() {
        viewModelScope.launch {
            runCatching {
                handleLoading(true)
                userInteractor.isAuthorized()
            }.onSuccess {
                handleRequestSuccess()
                isAuthorizedUserInternal.value = it
            }.onFailure {
                showMessage(R.string.error_base_message)
            }
        }
    }
}