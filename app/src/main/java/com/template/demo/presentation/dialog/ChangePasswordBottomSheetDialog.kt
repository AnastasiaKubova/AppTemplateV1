package com.template.demo.presentation.dialog

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.template.basecomponents.delegates.viewBinding
import com.template.demo.R
import com.template.demo.databinding.DialogEditPasswordBinding
import com.template.basecomponents.BaseFullScreenBottomSheetDialogFragment
import com.template.basecomponents.view.isEditTextValid
import com.template.basecomponents.view.isEquals

class ChangePasswordBottomSheetDialog: BaseFullScreenBottomSheetDialogFragment(R.layout.dialog_edit_password) {

    private val binding by viewBinding(DialogEditPasswordBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            editPasswordSave.setOnClickListener {
                val isTextValid =
                    editNewPasswordLayout.isEditTextValid(R.string.error_input_text_base) && editRepeatPasswordLayout.isEditTextValid(
                        R.string.error_input_text_base
                    )

                /* If texts are not valid then break check.*/
                if (!isTextValid) {
                    return@setOnClickListener
                }

                /* Send result if passwords are same. */
                if (editNewPasswordLayout.isEquals(
                        editRepeatPasswordLayout,
                        R.string.error_password_are_not_same
                    )
                ) {
                    setFragmentResult(
                        CHANGE_PASSWORD_DIALOG_RESULT_KEY,
                        bundleOf(CHANGE_PASSWORD_DIALOG_RESULT_NEW_VALUE to editNewPasswordLayout.text)
                    )
                    dismiss()
                }
            }
        }
    }

    companion object {
        const val CHANGE_PASSWORD_DIALOG_RESULT_KEY = "CHANGE_PASSWORD_DIALOG_RESULT_KEY"
        const val CHANGE_PASSWORD_DIALOG_RESULT_NEW_VALUE = "CHANGE_PASSWORD_DIALOG_RESULT_NEW_VALUE"
    }
}