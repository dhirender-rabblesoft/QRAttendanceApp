package com.app.qrcodescanner.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.AppViewModel
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivitySignupBinding
import com.app.qrcodescanner.extension.isEmailValid
import com.app.qrcodescanner.ui.LoginActivity

class SingupViewModel(application: Application) : AppViewModel(application) {
    private lateinit var binder: ActivitySignupBinding
    private lateinit var mContext: Context
    lateinit var baseActivity: KotlinBaseActivity

    fun setBinder(binding: ActivitySignupBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binding
        this.baseActivity = baseActivity
        this.mContext = binding.root.context
        setClick()
    }

    private fun setClick() {
        binder.loginbutton.setOnClickListener {
            if (validation()) {
                Toast.makeText(baseActivity, "Singup button click", Toast.LENGTH_LONG).show()
            }
        }

        binder.llogin.setOnClickListener {
            baseActivity.openA(LoginActivity::class)
        }
    }

    private fun validation(): Boolean {
        binder.namelayout.error = null
        binder.emaillayout.error = null
        binder.passwordlayout.error = null
        binder.confirmpasswordlayout.error = null
        if (binder.etusername.text.toString().trim().isEmpty()) {
            binder.namelayout.error = mContext.getString(R.string.enter_your_full_name)
            return false
        }
        if (binder.etemail.text.toString().trim().isEmpty()) {
            binder.emaillayout.error = mContext.getString(R.string.v_emailvalidation)
            return false
        }
        if (!isEmailValid(binder.etemail.text.toString().trim())) {
            binder.emaillayout.error = mContext.getString(R.string.v_validemail)
            return false
        }
        if (binder.etphone.text.toString().trim().isEmpty()) {
            binder.passwordlayout.error = mContext.getString(R.string.enter_phone_number)

            // showToast(mContext.getString(R.string.v_password))
            return false
        }
        if (binder.etpassword.text.toString().trim().isEmpty()) {
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
        if (binder.etphone.text.toString().trim().length < 10) {
            binder.passwordlayout.error = mContext.getString(R.string.phonelength)

            // showToast(mContext.getString(R.string.passwordlength))
            return false
        }

        return true

    }


}