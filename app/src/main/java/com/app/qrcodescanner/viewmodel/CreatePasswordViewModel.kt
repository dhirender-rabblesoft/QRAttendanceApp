package com.app.qrcodescanner.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.AppViewModel
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityCreatePasswordBinding
import com.app.qrcodescanner.reposiory.CommonRepository
import com.app.qrcodescanner.ui.LoginActivity
import com.app.qrcodescanner.utils.Keys
import com.google.gson.JsonObject

class CreatePasswordViewModel(application: Application) : AppViewModel(application)
{
    private lateinit var binder: ActivityCreatePasswordBinding
    private lateinit var mContext: Context
    lateinit var baseActivity: KotlinBaseActivity
    var bundle= Bundle()
    var  commonRepository= CommonRepository(application)


    fun setBinder(binding: ActivityCreatePasswordBinding, baseActivity: KotlinBaseActivity)
    {
        this.binder = binding
        this.mContext = binding.root.context
        this.baseActivity = baseActivity
        bundle = (mContext as Activity).intent.extras!!
        setClicks()
    }

    private fun setClicks()
    {
        binder.loginbutton.setOnClickListener {
            if (validation()) {
                otpverify()
            }
        }
    }
    private  fun otpverify()
    {
        val  jsonObject=JsonObject() 
        jsonObject.addProperty(Keys.email,bundle.getString(Keys.email))
        jsonObject.addProperty(Keys.code,bundle.getString(Keys.code))
        jsonObject.addProperty(Keys.password,binder.etpassword.text.toString().trim())
        jsonObject.addProperty(Keys.confirm_password,binder.etpassword.text.toString().trim())
        commonRepository.forgorpassword(baseActivity,Keys.RESETPASSWORD,jsonObject,ishowloader = true){
            baseActivity.openA(LoginActivity::class)
            baseActivity.finishAffinity()
        }
    }


    private fun validation(): Boolean
    {

        binder.passwordlayout.error = null
        binder.confirmpasswordlayout.error = null
        if (binder.etpassword.text.toString().trim().isEmpty())
        {
            binder.passwordlayout.error = mContext.getString(R.string.v_validpassword)
            return false
        }
        if (binder.etconfirmpassword.text.toString().trim().isEmpty()) {
            binder.confirmpasswordlayout.error = mContext.getString(R.string.v_comfirmpassword)
            return false
        }
        if (!binder.etconfirmpassword.text.toString().trim()
                .equals(binder.etpassword.text.toString().trim())
        ) {
            binder.confirmpasswordlayout.error = mContext.getString(R.string.passwordnotmatch)
            //   showToast(mContext.getString(R.string.passwordnotmatch))
            return false
        }
        return true
    }

}