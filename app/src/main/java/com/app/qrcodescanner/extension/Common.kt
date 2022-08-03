package com.app.qrcodescanner.extension

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.app.qrcodescanner.R
import java.util.regex.Pattern
infix fun ViewGroup.inflate(@LayoutRes view: Int): View {
    return LayoutInflater.from(context).inflate(view, this, false)
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}
fun AutoCompleteTextView.afterTextChangedDelayed(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        var timer: CountDownTimer? = null

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(editable: Editable?) {
            timer?.cancel()
            timer = object : CountDownTimer(1000, 500) {
                override fun onTick(millisUntilFinished: Long) {}
                override fun onFinish() {
                    afterTextChanged.invoke(editable.toString())
                }
            }.start()
        }
    })
}
fun String.capitalizesLetters(): String {
    return if (this.isEmpty())
        ""
    else this.split(' ').joinToString(" ") { it.replaceFirstChar { it.uppercase() } }
}
fun <T> T.isNotNull(): Boolean {
    return this != null
}
fun Activity.getDecorView(): View {
    return window.decorView
}
fun Context.showConfirmAlert(
    title:String="",
    message: String?, positiveText: String?
    , negativeText: String?
    , onConfirmed: () -> Unit = {}
    , onCancel: () -> Unit = { }

) {

    if (message.isNullOrEmpty()) return

    val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        AlertDialog.Builder(this, R.style.customAlertheme)
    } else {
        AlertDialog.Builder(this)
    }


    builder.setTitle(title).setMessage(message)
        .setCancelable(false)
        .setPositiveButton(positiveText) { dialog, _ ->
            onConfirmed.invoke()
            dialog.dismiss()
        }
        .setNegativeButton(negativeText) { dialog, _ ->
            onCancel.invoke()
            dialog.dismiss()
        }
        .setNeutralButton(""){dialog, _ ->

        }

    val alert = builder.create()
    alert.show()
}


fun isEmailValid(email: String): Boolean
{
    return Pattern.compile(
        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
    ).matcher(email).matches()


}