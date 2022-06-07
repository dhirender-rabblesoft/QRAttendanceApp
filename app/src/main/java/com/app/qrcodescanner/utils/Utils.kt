package com.app.qrcodescanner.utils

import android.R
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.widget.AutoCompleteTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.capitalizesLetters
import java.util.*

object Utils {

    //    const val RABBLESOFT_LAT = "30.8934659"
//    const val RABBLESOFT_LNG = "75.8290866"
    var RABBLESOFT_LAT = "30.8934667"
    var RABBLESOFT_LNG = "75.8290889"


    fun setDialogAttributes(dialog: Dialog, height: Int) {
        val window = dialog.window ?: return
        window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, height)
        window.setGravity(Gravity.CENTER)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun shoedatepicker(
        baseActivity: KotlinBaseActivity,
        lblDate: AutoCompleteTextView,
        onConfirmed: () -> Unit = {}
    ) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            baseActivity,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in textbox
                var month1 = monthOfYear
                ++month1
                lblDate.setText("" + dayOfMonth.toString() + "-" + month1 + "-" + year.toString())
                onConfirmed.invoke()
            },
            year,
            month,
            day
        )
        dpd.setButton(
            DialogInterface.BUTTON_NEGATIVE,
            baseActivity.getString(R.string.cancel).capitalizesLetters(),
            DialogInterface.OnClickListener { dialog, which ->
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    // Do Stuff
                    onConfirmed.invoke()
                }
            })

        dpd.show()
    }
}