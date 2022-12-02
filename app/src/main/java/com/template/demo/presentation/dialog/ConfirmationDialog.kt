package com.template.demo.presentation.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs

/*
 * Confirm dialog ex: https://developer.android.com/guide/fragments/dialogs
 * Set fragment result ex: https://developer.android.com/guide/fragments/communicate
 */
class ConfirmationDialog: DialogFragment() {

    private val args: ConfirmationDialogArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(args.confirmTitle)
            .setMessage(args.confirmMessage)
            .setPositiveButton(args.positiveButton) { _, _ ->
                setResult(CONFIRM_DIALOG_RESULT_POSITIVE_RESULT)
                dismiss()
            }
            .setNegativeButton (args.negativeButton) { _, _ ->
                setResult(CONFIRM_DIALOG_RESULT_NEGATIVE_RESULT)
                dismiss()
            }
            .setCancelable(true)
            .create()

    private fun setResult(result: String) {
        setFragmentResult(CONFIRM_DIALOG_RESULT_KEY, bundleOf(CONFIRM_DIALOG_RESULT_VALUE to result))
    }

    companion object {
        const val CONFIRM_DIALOG_RESULT_KEY = "CONFIRM_DIALOG_RESULT_KEY"
        const val CONFIRM_DIALOG_RESULT_VALUE = "CONFIRM_DIALOG_RESULT_VALUE"
        const val CONFIRM_DIALOG_RESULT_POSITIVE_RESULT = "CONFIRM_DIALOG_RESULT_POSITIVE_RESULT"
        const val CONFIRM_DIALOG_RESULT_NEGATIVE_RESULT = "CONFIRM_DIALOG_RESULT_NEGATIVE_RESULT"
    }
}