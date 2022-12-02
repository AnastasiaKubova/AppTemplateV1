package com.template.demo.presentation.fragment.base

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.template.demo.presentation.fragment.data.ErrorVO

open class BaseViewModel: ViewModel() {

    /**
     * Show snackbar with error text.
     */
    private val showMessageInternal: MutableLiveData<Int> = MutableLiveData()
    open val showMessage: LiveData<Int> = showMessageInternal

    /**
     * Show view with specific error.
     */
    private val showErrorViewInternal: MutableLiveData<ErrorVO?> = MutableLiveData()
    open val showErrorView: LiveData<ErrorVO?> = showErrorViewInternal

    /**
     * Show or hide progress.
     */
    private val isLoadingInProgressInternal: MutableLiveData<Boolean> = MutableLiveData()
    open val isLoadingInProgress: LiveData<Boolean> = isLoadingInProgressInternal

    /**
     * Handle request error and show error view.
     */
    protected fun handleRequestError(error: Throwable?) {
        isLoadingInProgressInternal.postValue(false)
        when (error) {
            null -> {
                showErrorViewInternal.postValue(null)
            }
            is NetworkErrorException -> {
                showErrorViewInternal.postValue(ErrorVO.NO_INTERNET)
            }
            else -> {
                showErrorViewInternal.postValue(ErrorVO.NETWORK_ERROR)
            }
        }
    }

    /**
     * Handle loading state and show or hide progress.
     */
    protected fun handleLoading(isLoading: Boolean) {
        showErrorViewInternal.postValue(null)
        isLoadingInProgressInternal.postValue(isLoading)
    }

    /**
     * Handle success request.
     */
    protected fun handleRequestSuccess() {
        showErrorViewInternal.postValue(null)
        isLoadingInProgressInternal.postValue(false)
    }

    /**
     * Show error message in snackbar.
     */
    fun showMessage(message: Int) {
        isLoadingInProgressInternal.postValue(false)
        showMessageInternal.postValue(message)
    }
}