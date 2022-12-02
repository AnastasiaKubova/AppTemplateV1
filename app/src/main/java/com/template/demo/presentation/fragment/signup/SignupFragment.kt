package com.template.demo.presentation.fragment.signup

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.template.basecomponents.delegates.viewBinding
import com.template.basecomponents.view.isEditTextValid
import com.template.basecomponents.view.isEquals
import com.template.demo.R
import com.template.demo.databinding.FmtSignupBinding
import com.template.demo.presentation.fragment.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupFragment: BaseFragment(R.layout.fmt_signup) {

    private val binding by viewBinding(FmtSignupBinding::bind)
    override val isBackMenuButtonVisible = true
    override val isBottomMenuVisible = false
    override val viewModel: SignupViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            /* Handle register button click. */
            signupButton.setOnClickListener { handleRegisterClick() }
        }

        /* Observe to live data. */
        viewModel.isUserRegistered.observe(viewLifecycleOwner, ::handleUserRegistrationResult)
    }

    /**
     * Handle register button click. Verify user data and try to register account.
     */
    private fun handleRegisterClick() {
        with(binding) {

            /* Verify that all inputted data are valid. */
            val isAllDataValid = signupEmailLayout.isEditTextValid(R.string.error_input_text_base)
                    && signupNameLayout.isEditTextValid(R.string.error_input_text_base)
                    && signupPasswordLayout.isEditTextValid(R.string.error_input_text_base)
                    && signupRepeatPasswordLayout.isEditTextValid(R.string.error_input_text_base)

            if (!isAllDataValid) return

            /* Verify that passwords are same. */
            val isPasswordIsValid = signupPasswordLayout.isEquals(
                signupRepeatPasswordLayout,
                R.string.error_password_are_not_same
            )

            /* If not all data are valid, then stop registration process. */
            if (!isPasswordIsValid) return

            /* Otherwise try to register user. */
            viewModel.tryRegisterUser(
                signupEmailLayout.text.trim(),
                signupPasswordLayout.text.trim(),
                signupNameLayout.text.trim()
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
}