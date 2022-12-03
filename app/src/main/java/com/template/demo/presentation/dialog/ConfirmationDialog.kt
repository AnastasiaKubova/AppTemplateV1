package com.template.demo.presentation.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
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
                dismiss()

                /*
                 * TODO FIX:
                 * java.lang.IllegalArgumentException: Navigation action/destination com.template.demo:id/action_navigation_settings_fragment_to_navigation_login_fragment cannot be found from the current destination Destination(com.template.demo:id/navigation_confirmation_dialog) label=ConfirmationDialog.
                 */
                findNavController().navigateUp()
                setResult(CONFIRM_DIALOG_RESULT_POSITIVE_RESULT)
            }
            .setNegativeButton (args.negativeButton) { _, _ ->
                dismiss()
                findNavController().navigateUp()
                setResult(CONFIRM_DIALOG_RESULT_NEGATIVE_RESULT)
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