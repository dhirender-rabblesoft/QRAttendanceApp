package com.app.qrcodescanner.viewmodel

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.AppViewModel
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityForgotPasswordBinding
import com.app.qrcodescanner.extension.isEmailValid
import com.app.qrcodescanner.extension.isNotNull
import com.app.qrcodescanner.reposiory.CommonRepository
import com.app.qrcodescanner.ui.OTPVerify
import com.app.qrcodescanner.utils.Keys
import com.google.gson.JsonObject

class ForgotPassowordViewModel(application: Application) : AppViewModel(application) {
    private lateinit var binder: ActivityForgotPasswordBinding
    private lateinit var mContext: Context
    lateinit var baseActivity: KotlinBaseActivity
    var  commonRepository= CommonRepository(application)

    fun setBinder(binding: ActivityForgotPasswordBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binding
        this.mContext = binding.root.context
        this.baseActivity = baseActivity
        setClicks()
    }

    private fun setClicks() {
        binder.loginbutton.setOnClickListener {
            if (validation()) {
                forgotApi()

            }

        }
    }

    private fun validation(): Boolean {
        binder.emaillayout.error = null

        if (binder.etemail.text.toString().trim().isEmpty()) {
            binder.emaillayout.error = mContext.getString(R.string.v_emailvalidation)
            return false
        }
        if (!isEmailValid(binder.etemail.text.toString().trim())) {
            binder.emaillayout.error = mContext.getString(R.string.v_validemail)
            return false
        }
        return true
    }
    private  fun forgotApi()
    {
        val jsonobj= JsonObject()
        jsonobj.addProperty(Keys.email,binder.etemail.text.toString().trim())
         commonRepository.forgorpassword(baseActivity, Keys.FORGOTPASSWOD,jsonobj,true){
            if (it.data.isNotNull())
            {
                  val bundle=Bundle()
                  bundle.putString(Keys.email,binder.etemail.text.toString().trim())
                 baseActivity.openA(OTPVerify::class,bundle)
            }
        }
    }


}