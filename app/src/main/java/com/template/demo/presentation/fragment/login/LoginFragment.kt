package com.template.demo.presentation.fragment.login

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.template.basecomponents.delegates.viewBinding
import com.template.basecomponents.utils.allTruth
import com.template.basecomponents.view.isEditTextValid
import com.template.demo.R
import com.template.demo.databinding.FmtLoginBinding
import com.template.demo.presentation.fragment.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment: BaseFragment(R.layout.fmt_login) {

    private val binding by viewBinding(FmtLoginBinding::bind)
    override val isBottomMenuVisible = false
    override val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Verify that user have already authorized. */
        viewModel.isUserAuthorized()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Handle login action. */
        binding.loginButton.setOnClickListener { handleLoginClick() }

        /* Handle signup action. */
        binding.loginSignupButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
        }

        /* Observe to live data. */
        viewModel.isAuthorizedUser.observe(viewLifecycleOwner, ::handleUserAuthorizationResult)
    }
    /**
     * Handle login button. Verify user's data and try to login in to account.
     */
    private fun handleLoginClick() {
        with(binding) {

            /* Check that all data were inputted correctly. */
            allTruth(loginEmailLayout.isEditTextValid(R.string.error_input_text_base), loginPasswordLayout.isEditTextValid(
                R.string.error_input_text_base
            )) { isTruth ->
                if (!isTruth) return@with
            }

            /* Otherwise try to login user. */
            viewModel.tryLoginUser(
                loginEmailLayout.text.trim(),
                loginPasswordLayout.text.trim()
            )
        }
    }

    /**
     * Handle user authorization result.
     */
    private fun handleUserAuthorizationResult(isAuthorized: Boolean?) {
        isAuthorized ?: return
        if (isAuthorized) {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavigationHome())
        }
    }
}