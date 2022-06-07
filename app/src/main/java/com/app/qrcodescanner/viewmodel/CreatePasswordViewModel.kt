package com.app.qrcodescanner.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.AppViewModel
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityCreatePasswordBinding
import com.app.qrcodescanner.ui.LoginActivity

class CreatePasswordViewModel(application: Application) : AppViewModel(application) {

    private lateinit var binder: ActivityCreatePasswordBinding
    private lateinit var mContext: Context
    lateinit var baseActivity: KotlinBaseActivity
    fun setBinder(binding: ActivityCreatePasswordBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binding
        this.mContext = binding.root.context
        this.baseActivity = baseActivity
        setClicks()
    }

    private fun setClicks() {
        binder.loginbutton.setOnClickListener {
            if (validation()) {
                baseActivity.openA(LoginActivity::class)
                baseActivity.finishAffinity()
                Toast.makeText(baseActivity, "Create screen button click", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun validation(): Boolean {

        binder.passwordlayout.error = null
        binder.confirmpasswordlayout.error = null
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
        return true
    }

}