package com.template.demo.presentation.fragment.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.template.demo.R
import com.template.demo.domain.interactor.user.UserInteractor
import com.template.demo.presentation.fragment.base.BaseViewModel
import kotlinx.coroutines.launch

class SignupViewModel(
    private val userInteractor: UserInteractor
): BaseViewModel() {

    /**
     * Follow that user is authorized in app or not.
     */
    private val isUserRegisteredInternal: MutableLiveData<Boolean?> = MutableLiveData(null)
    val isUserRegistered: LiveData<Boolean?> = isUserRegisteredInternal

    /**
     * Try to register user.
     */
    fun tryRegisterUser(
        email: String,
        password: String,
        name: String,
    ) {
        viewModelScope.launch {
            runCatching {
                handleLoading(true)
                userInteractor.registerUser(email, password, name)
            }.onSuccess {
                handleRequestSuccess()
                isUserRegisteredInternal.value = it
            }.onFailure {
                showMessage(R.string.error_base_message)
            }
        }
    }
}