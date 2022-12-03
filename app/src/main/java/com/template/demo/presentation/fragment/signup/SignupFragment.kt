package com.template.demo.presentation.fragment.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.template.basecomponents.delegates.viewBinding
import com.template.basecomponents.utils.allTruth
import com.template.basecomponents.view.isEditTextValid
import com.template.basecomponents.view.isEquals
import com.template.demo.R
import com.template.demo.databinding.FmtSignupBinding
import com.template.demo.presentation.dialog.DataPickerDialog.Companion.DATA_PICKER_DIALOG_RESULT_KEY
import com.template.demo.presentation.dialog.DataPickerDialog.Companion.DATA_PICKER_DIALOG_RESULT_VALUE
import com.template.demo.presentation.fragment.base.BaseFragment
import com.template.utils.toTimeFormat
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupFragment: BaseFragment(R.layout.fmt_signup) {

    private val binding by viewBinding(FmtSignupBinding::bind)
    override val isBackMenuButtonVisible = true
    override val isBottomMenuVisible = false
    override val viewModel: SignupViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleFragmentResults()
        with(binding) {

            /* Handle select data action. */
            signupDataPicker.setOnClickListener {
                findNavController().navigate(SignupFragmentDirections.actionNavigationSignupFragmentToDataPickerDialog(R.string.no_birthday))
            }

            /* Handle register button click. */
            signupButton.setOnClickListener { handleRegisterClick() }
        }

        /* Observe to live data. */
        viewModel.isUserRegistered.observe(viewLifecycleOwner, ::handleUserRegistrationResult)
        viewModel.birthday.observe(viewLifecycleOwner, ::handleUserBirthdayChoice)
    }

    /**
     * Handle register button click. Verify user data and try to register account.
     */
    private fun handleRegisterClick() {
        with(binding) {

            /* Verify that all inputted data are valid. */
            allTruth(
                signupEmail.isEditTextValid(R.string.error_input_text_base),
                signupName.isEditTextValid(R.string.error_input_text_base),
                signupPassword.isEditTextValid(R.string.error_input_text_base),
                signupRepeatPassword.isEditTextValid(R.string.error_input_text_base)
            ) { isTruth ->
                if (!isTruth) return@with
            }

            /* Verify that passwords are same. */
            if (!signupPassword.isEquals(signupRepeatPassword, R.string.error_password_are_not_same)
            ) {
                return
            }

            /* Otherwise try to register user. */
            viewModel.tryRegisterUser(
                signupEmail.text.trim(),
                signupPassword.text.trim(),
                signupName.text.trim(),
            )
        }
    }

    /**
     * Handle user registration result.
     */
    private fun handleUserRegistrationResult(isRegistered: Boolean?) {
        isRegistered ?: return
        if (isRegistered) {
            findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToNavigationHome())
        } else {
            viewModel.showMessage(R.string.error_base_message)
        }
    }

    /**
     * Show user's selected birthday.
     */
    private fun handleUserBirthdayChoice(birthday: Long) {
        binding.signupDataPicker.text = birthday.toTimeFormat()
    }

    private fun handleFragmentResults() {

        /* Handle change user birthday. */
        setFragmentResultListener(DATA_PICKER_DIALOG_RESULT_KEY) { _, bundle ->
            viewModel.handleSelectedBirthday(bundle.getLong(DATA_PICKER_DIALOG_RESULT_VALUE))
        }
    }
}