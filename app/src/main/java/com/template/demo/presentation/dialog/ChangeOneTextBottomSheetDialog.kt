package com.template.demo.presentation.dialog

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.template.basecomponents.delegates.viewBinding
import com.template.demo.R
import com.template.demo.databinding.DialogEditOneTextBinding
import com.template.basecomponents.BaseFullScreenBottomSheetDialogFragment
import com.template.basecomponents.view.isEditTextValid

class ChangeOneTextBottomSheetDialog: BaseFullScreenBottomSheetDialogFragment(R.layout.dialog_edit_one_text) {

    private val binding by viewBinding(DialogEditOneTextBinding::bind)
    private val args: ChangeOneTextBottomSheetDialogArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            editOneTextTitle.setText(args.dialogTitle)
            editOneTextLayout.text = args.targetText
            editOneTextLayout.hint = requireContext().getString(args.textHint)
            editOneTextSave.setOnClickListener {
                if (editOneTextLayout.isEditTextValid(R.string.error_input_text_base)) {
                    val newValue = editOneTextLayout.text.trim()
                    setFragmentResult(
                        "${args.identityTag}$ONE_CHANGE_DIALOG_RESULT_KEY",
                        bundleOf(
                            ONE_CHANGE_DIALOG_RESULT_NEW_VALUE to newValue
                        )
                    )
                    dismiss()
                }
            }
        }
    }

    companion object {
        const val ONE_CHANGE_DIALOG_RESULT_KEY = "ONE_CHANGE_DIALOG_RESULT_KEY"
        const val ONE_CHANGE_DIALOG_RESULT_NEW_VALUE = "ONE_CHANGE_DIALOG_RESULT_NEW_VALUE"
        const val IDENTITY_CHANGE_NAME_TAG = "CHANGE_NAME"
        const val IDENTITY_CHANGE_EMAIL_TAG = "CHANGE_EMAIL"
    }
}