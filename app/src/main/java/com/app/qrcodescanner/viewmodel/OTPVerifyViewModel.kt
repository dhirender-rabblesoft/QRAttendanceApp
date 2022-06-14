package com.app.qrcodescanner.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.AppViewModel
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityOtpverifyBinding
import com.app.qrcodescanner.ui.CreatePassword

class OTPVerifyViewModel(application: Application) : AppViewModel(application)
{
    lateinit var binder: ActivityOtpverifyBinding
    lateinit var mContext: Context
    lateinit var baseActivity: KotlinBaseActivity
    fun setBinder(binding: ActivityOtpverifyBinding, baseActivity: KotlinBaseActivity)
    {
        this.baseActivity = baseActivity
        this.binder = binding
        this.mContext = binding.root.context
        setClick()
    }

    private fun setClick()
    {
        binder.loginbutton.setOnClickListener {
            if (validation()) {
                baseActivity.openA(CreatePassword::class)
                Toast.makeText(baseActivity, "OTP screen button click", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validation(): Boolean {
        binder.codelayout.error = null
        if (binder.etcode.text.toString().trim().isEmpty()) {
            binder.codelayout.error = mContext.getString(R.string.enter_code)
            return false
        }
        if (binder.etcode.text.toString().trim().length < 4) {
            binder.codelayout.error = mContext.getString(R.string.valid_code)
            return false
        }
        binder.tvreset.setOnClickListener {
            Toast.makeText(baseActivity, "OTP Resend button click", Toast.LENGTH_LONG).show()
        }
        return true

    }
}