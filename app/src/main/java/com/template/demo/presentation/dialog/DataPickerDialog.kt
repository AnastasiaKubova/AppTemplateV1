package com.template.demo.presentation.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.template.demo.R
import java.util.*

class DataPickerDialog: DialogFragment() {

    private val args: DataPickerDialogArgs by navArgs()

    override fun onResume() {
        super.onResume()
        MaterialDatePicker.Builder.datePicker()
            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .setSelection(args.targetDate?.toLong() ?: Calendar.getInstance().timeInMillis)
            .setTitleText(args.dialogTitle)
            .build()
            .apply {
                addOnPositiveButtonClickListener { result ->
                    setFragmentResult(DATA_PICKER_DIALOG_RESULT_KEY, bundleOf(DATA_PICKER_DIALOG_RESULT_VALUE to result))
                    this@DataPickerDialog.dismiss()
                }
                addOnNegativeButtonClickListener {
                    this@DataPickerDialog.dismiss()
                }
                addOnDismissListener {
                    this@DataPickerDialog.dismiss()
                }
            }.show(childFragmentManager, TAG)
    }

    companion object {
        private const val TAG = "DataPickerDialog"
        const val DATA_PICKER_DIALOG_RESULT_KEY = "DATA_PICKER_DIALOG_RESULT_KEY"
        const val DATA_PICKER_DIALOG_RESULT_VALUE = "DATA_PICKER_DIALOG_RESULT_VALUE"
    }
}