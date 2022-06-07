package com.app.qrcodescanner.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.AppViewModel
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityLoginBinding
import com.app.qrcodescanner.extension.isEmailValid
import com.app.qrcodescanner.ui.ForgotPassword
import com.app.qrcodescanner.ui.HomeScreenActivity
import com.app.qrcodescanner.ui.MainActivity
import com.app.qrcodescanner.ui.SignupActivity

class LoginViewModel(application: Application) : AppViewModel(application) {

    private lateinit var binder: ActivityLoginBinding
    private lateinit var mContext: Context
    lateinit var baseActivity: KotlinBaseActivity
    fun setBinder(binder: ActivityLoginBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binder
        this.mContext = binder.root.context
        this.baseActivity = baseActivity
        this.binder.viewModel = this
        setClick()
    }

    private fun validation(): Boolean {

        binder.passwordlayout.error = null
        binder.emaillayout.error = null

        if (binder.etemail.text.toString().trim().isEmpty()) {
            binder.emaillayout.error = mContext.getString(R.string.v_emailvalidation)
            return false
        }
        if (!isEmailValid(binder.etemail.text.toString().trim())) {
            binder.emaillayout.error = mContext.getString(R.string.v_validemail)
            return false
        }

        if (binder.etpassword.text.toString().trim().isEmpty()) {
            binder.passwordlayout.error = mContext.getString(R.string.v_validpassword)
        }
        return true

    }

    private fun setClick() {

        binder.tvforgotpasssword.setOnClickListener {
            baseActivity.openA(ForgotPassword::class)
        }

        binder.loginbutton.setOnClickListener {

            //main code
//            if (validation()) {
//                baseActivity.openA(MainActivity::class)
//                baseActivity.finishAffinity()
//                Toast.makeText(baseActivity, "sdsdfdsdsfs", Toast.LENGTH_LONG).show()
//            }
            baseActivity.openA(HomeScreenActivity::class)
        }
        binder.llsignup.setOnClickListener {
            baseActivity.openA(SignupActivity::class)

        }
        binder.tvforgotpasssword.setOnClickListener {
            baseActivity.openA(ForgotPassword::class)
        }
    }


}