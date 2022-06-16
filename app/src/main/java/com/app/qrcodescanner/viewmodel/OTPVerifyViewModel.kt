package com.app.qrcodescanner.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.AppViewModel
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityOtpverifyBinding
import com.app.qrcodescanner.extension.isNotNull
import com.app.qrcodescanner.reposiory.CommonRepository
import com.app.qrcodescanner.ui.CreatePassword
import com.app.qrcodescanner.ui.OTPVerify
import com.app.qrcodescanner.utils.Keys
import com.google.gson.JsonObject

class OTPVerifyViewModel(application: Application) : AppViewModel(application)
{
    lateinit var binder: ActivityOtpverifyBinding
    lateinit var mContext: Context
    var  commonRepository=CommonRepository(application)

    lateinit var baseActivity: KotlinBaseActivity
    var bundle= Bundle()
    fun setBinder(binding: ActivityOtpverifyBinding, baseActivity: KotlinBaseActivity)
    {
        this.baseActivity = baseActivity
        this.binder = binding
        this.mContext = binding.root.context
        bundle = (mContext as Activity).intent.extras!!
        setClick()
    }

    private fun setClick()
    {
        binder.loginbutton.setOnClickListener {
            if (validation()) {
                val  bundle1=Bundle()
                bundle1.putString(Keys.email,bundle.getString(Keys.email))
                bundle1.putString(Keys.code,binder.etcode.text.toString().trim())
                baseActivity.openA(CreatePassword::class,bundle1)
             }
        }
        binder.tvreset.setOnClickListener {
            resetpassword()
        }
    }
    private  fun resetpassword()
    {
        val jsonobj= JsonObject()
        jsonobj.addProperty(Keys.email,bundle.getString(Keys.email))
        commonRepository.forgorpassword(baseActivity, Keys.FORGOTPASSWOD,jsonobj,true){
            if (it.data.isNotNull())
            {
                binder.etcode.setText("")
              baseActivity.customSnackBar("Otp sent to your registered email ",false)
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
         }
        return true

    }
}