package com.template.demo.presentation.fragment.settings

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.template.basecomponents.delegates.viewBinding
import com.template.demo.R
import com.template.demo.databinding.FmtSettingBinding
import com.template.demo.presentation.dialog.ChangeOneTextBottomSheetDialog.Companion.IDENTITY_CHANGE_EMAIL_TAG
import com.template.demo.presentation.dialog.ChangeOneTextBottomSheetDialog.Companion.IDENTITY_CHANGE_NAME_TAG
import com.template.demo.presentation.dialog.ChangeOneTextBottomSheetDialog.Companion.ONE_CHANGE_DIALOG_RESULT_KEY
import com.template.demo.presentation.dialog.ChangeOneTextBottomSheetDialog.Companion.ONE_CHANGE_DIALOG_RESULT_NEW_VALUE
import com.template.demo.presentation.dialog.ChangePasswordBottomSheetDialog.Companion.CHANGE_PASSWORD_DIALOG_RESULT_KEY
import com.template.demo.presentation.dialog.ChangePasswordBottomSheetDialog.Companion.CHANGE_PASSWORD_DIALOG_RESULT_NEW_VALUE
import com.template.demo.presentation.dialog.ConfirmationDialog.Companion.CONFIRM_DIALOG_RESULT_KEY
import com.template.demo.presentation.dialog.ConfirmationDialog.Companion.CONFIRM_DIALOG_RESULT_POSITIVE_RESULT
import com.template.demo.presentation.dialog.ConfirmationDialog.Companion.CONFIRM_DIALOG_RESULT_VALUE
import com.template.demo.presentation.dialog.DataPickerDialog.Companion.DATA_PICKER_DIALOG_RESULT_KEY
import com.template.demo.presentation.dialog.DataPickerDialog.Companion.DATA_PICKER_DIALOG_RESULT_VALUE
import com.template.demo.presentation.dialog.DataPickerDialog.Companion.DEFAULT_TARGET_DATE_VALUE
import com.template.demo.presentation.fragment.base.BaseFragment
import com.template.demo.presentation.fragment.settings.data.UserSettingsVO
import com.template.utils.toTimeFormat
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment: BaseFragment(R.layout.fmt_setting) {

    private val binding by viewBinding(FmtSettingBinding::bind)
    override val isBottomMenuVisible = false
    override val isBackMenuButtonVisible = true
    override val viewModel: SettingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Start loading user data. */
        viewModel.loadUserData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleFragmentResults()

        /* Observe to live data. */
        viewModel.userData.observe(viewLifecycleOwner, ::handleLoadedUserData)
    }

    /**
     * Change change birthday.
     */
    private fun handleChangeBirthday() {
        binding.settingDataPicker.setOnClickListener {
            findNavController().navigate(
                SettingsFragmentDirections.actionNavigationSettingsFragmentToDataPickerDialog(
                    R.string.birthday,
                    viewModel.userData.value?.birthday ?: DEFAULT_TARGET_DATE_VALUE
                )
            )
        }
    }

    /**
     * Handle exit action.
     */
    private fun handleExitClick() {
        binding.settingsExitButton.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToConfirmationDialog(
                R.string.dialog_exit_title,
                R.string.dialog_exit_subtitle,
                R.string.dialog_positive_button,
                R.string.dialog_negative_button,
            ))
        }
    }

    /**
     * Handle password change action.
     */
    private fun handleChangePassword() {
        binding.settingsPassword.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToChangePasswordBottomSheetDialog())
        }
    }

    /**
     * Handle change name action.
     */
    private fun handleChangeName() {
        with(binding) {
            settingsName.setOnClickListener {
                findNavController().navigate(
                    SettingsFragmentDirections.actionNavigationSettingsFragmentToChangeOneTextBottomSheetDialog(
                        R.string.dialog_change_name,
                        R.string.name,
                        settingsName.text.toString(),
                        IDENTITY_CHANGE_NAME_TAG
                    )
                )
            }
        }
    }

    /**
     * Handle change email action.
     */
    private fun handleChangeEmail() {
        with(binding) {
            settingsEmail.setOnClickListener {
                findNavController().navigate(
                    SettingsFragmentDirections.actionNavigationSettingsFragmentToChangeOneTextBottomSheetDialog(
                        R.string.dialog_change_email,
                        R.string.email,
                        settingsEmail.text.toString(),
                        IDENTITY_CHANGE_EMAIL_TAG
                    )
                )
            }
        }
    }

    /**
     * Handle change theme action.
     */
    private fun handleTheme() {
        with(binding) {
            settingsThemeGroup.setOnCheckedChangeListener { group, checkedId ->
                viewModel.handleChangeTheme(group.indexOfChild(group.findViewById<RadioButton>(checkedId)))
            }
        }
    }

    /**
     * Handle fragment results.
     */
    private fun handleFragmentResults() {

        /* Handle change user birthday. */
        setFragmentResultListener(DATA_PICKER_DIALOG_RESULT_KEY) { _, bundle ->
            viewModel.handleChangeBirthday(bundle.getLong(DATA_PICKER_DIALOG_RESULT_VALUE))
        }

        /* Handle user exit confirmation result. */
        setFragmentResultListener(CONFIRM_DIALOG_RESULT_KEY) { _, bundle ->
            if(bundle.getString(CONFIRM_DIALOG_RESULT_VALUE) == CONFIRM_DIALOG_RESULT_POSITIVE_RESULT) {
                viewModel.clearUserLoginData()
                findNavController().navigate(SettingsFragmentDirections.actionNavigationSettingsFragmentToNavigationLoginFragment())
            }
        }

        /* Handle change user name. */
        setFragmentResultListener(IDENTITY_CHANGE_NAME_TAG + ONE_CHANGE_DIALOG_RESULT_KEY) { _, bundle ->
            viewModel.handleChangeName(bundle.getString(ONE_CHANGE_DIALOG_RESULT_NEW_VALUE))
        }

        /* Handle change user email. */
        setFragmentResultListener(IDENTITY_CHANGE_EMAIL_TAG + ONE_CHANGE_DIALOG_RESULT_KEY) { _, bundle ->
            viewModel.handleChangeEmail(bundle.getString(ONE_CHANGE_DIALOG_RESULT_NEW_VALUE))
        }

        /* Handle change user password. */
        setFragmentResultListener(CHANGE_PASSWORD_DIALOG_RESULT_KEY) { _, bundle ->
            viewModel.handleChangePassword(bundle.getString(CHANGE_PASSWORD_DIALOG_RESULT_NEW_VALUE))
        }
    }

    /**
     * Handle loaded data for showing in settings.
     */
    private fun handleLoadedUserData(data: UserSettingsVO) {
        with(binding) {
            settingsName.text = data.name
            settingsEmail.text = data.email
            settingsPassword.text = data.password
            settingDataPicker.isVisible = data.birthday != null
            settingDataPicker.text = data.birthday?.toTimeFormat()
            (settingsThemeGroup.getChildAt(data.themeType.tag) as RadioButton).isChecked = true
        }

        /* Handle exit action. */
        handleExitClick()

        /* Change user name action. */
        handleChangeName()

        /* Change password action. */
        handleChangePassword()

        /* Change email action. */
        handleChangeEmail()

        /* Change birthday action. */
        handleChangeBirthday()

        /* Change theme. */
        handleTheme()
    }
}