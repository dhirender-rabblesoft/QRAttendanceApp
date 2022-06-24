package com.app.qrcodescanner.utils

import android.R
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.capitalizesLetters
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    //    const val RABBLESOFT_LAT = "30.8934659"
//    const val RABBLESOFT_LNG = "75.8290866"
    var RABBLESOFT_LAT = "30.8934667"
    var RABBLESOFT_LNG = "75.8290889"
    var WEBURL = "75.8290889"
    const val DATETIMEFORMAT="yyyy-MM-dd HH:mm:ss"
    const val DATEFORMAT="dd-MMM-yyyy"
    const val DATEFORMAT3="dd-MM-yyyy"
    const val TIMEFORMAT="hh:mm aa"

    const val DATEFORMAT2="yyyy-MM-dd"
    const val TIMEFORMAT2="hh:mm:ss"

    fun formateDateFromstring(inputFormat: String, outputFormat: String, inputDate: String): String {
        var parsed: Date? = null
        var outputDate = ""
        var df_input = SimpleDateFormat(inputFormat, Locale.getDefault())
        var df_output = SimpleDateFormat(outputFormat, Locale.getDefault())
        try {
            parsed = df_input.parse(inputDate)
            outputDate = df_output.format(parsed)
        } catch (e: ParseException) {
        }
        return outputDate
    }


    fun hideKeyBoard(c: Context, v: View) {
        val imm = c
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }
    fun getcurrentdate():String
    {
        val c = Calendar.getInstance()
        val sdf = SimpleDateFormat(DATEFORMAT2)
        val strDate = sdf.format(c.time)
        return strDate
    }
    fun getcurrenttime():String
    {
        val c = Calendar.getInstance()
        val sdf = SimpleDateFormat(TIMEFORMAT2)
        val strDate = sdf.format(c.time)
        return strDate
    }

    fun setDialogAttributes(dialog: Dialog, height: Int) {
        val window = dialog.window ?: return
        window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, height)
        window.setGravity(Gravity.CENTER)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    fun getMultiPart(key: String?, file: String?): MultipartBody.Part?
    {
        return MultipartBody.Part.createFormData(key!!, file!!)
    }
    fun getMultiPart(key: String?, file: Any?): MultipartBody.Part?
    {
        return MultipartBody.Part.createFormData(key!!, file!!.toString())
    }
    fun getMultiPart(key: String?, file: File): MultipartBody.Part?
    {
        val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(key!!, file.name, requestFile)
    }

    fun shoedatepicker(
        baseActivity: KotlinBaseActivity,
        lblDate: AppCompatTextView,
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