package edu.msoe.receiptstorageapp

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import java.util.Calendar
import java.util.GregorianCalendar

/**
 * DatePickerFragment Class: Provides a DialogFragment for allowing users to set the date of a
 * receipt.
 */
class DatePickerFragment : DialogFragment() {

    private val args: DatePickerFragmentArgs by navArgs()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dateListener = DatePickerDialog.OnDateSetListener {
                _: DatePicker, year: Int, month: Int, day: Int ->

            val result = GregorianCalendar(year, month, day).time

            setFragmentResult(REQUEST_KEY_DATE,
                bundleOf(BUNDLE_KEY_DATE to result))
        }



        val calendar = Calendar.getInstance()

        calendar.time = args.receiptDate

        val year = calendar.get(Calendar.YEAR)

        val month = calendar.get(Calendar.MONTH)

        val day = calendar.get(Calendar.DAY_OF_MONTH)

        var datePickerDialog = DatePickerDialog(requireContext(), dateListener, year, month, day)

        // Only allow current/past dates
        datePickerDialog.datePicker.maxDate = (System.currentTimeMillis() - 1000)

        return datePickerDialog
    }
    companion object {
        const val REQUEST_KEY_DATE = "REQUEST_KEY_DATE"
        const val BUNDLE_KEY_DATE = "BUNDLE_KEY_DATE"
    }
}