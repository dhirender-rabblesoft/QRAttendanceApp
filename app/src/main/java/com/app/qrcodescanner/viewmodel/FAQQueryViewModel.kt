package com.app.qrcodescanner.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.AppViewModel
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityFaqqueryBinding
import com.app.qrcodescanner.databinding.ActivitySignupBinding
import com.app.qrcodescanner.extension.invisible
import com.app.qrcodescanner.extension.isEmailValid
import com.app.qrcodescanner.ui.LoginActivity
import kotlinx.android.synthetic.main.activity_signup.view.*
import kotlinx.android.synthetic.main.activity_signup.view.tvtitle
import kotlinx.android.synthetic.main.common_toolbar.view.*

class FAQQueryViewModel(application: Application) : AppViewModel(application) {
    private lateinit var binder: ActivityFaqqueryBinding
    private lateinit var mContext: Context
    lateinit var baseActivity: KotlinBaseActivity

    fun setBinder(binding: ActivityFaqqueryBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binding
        this.baseActivity = baseActivity
        this.mContext = binding.root.context
        setClick()
        settoolbar()
    }

    private fun setClick() {
        binder.loginbutton.setOnClickListener {
            if (isValidation()) {
                Toast.makeText(baseActivity, "Query button click", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun settoolbar() {
        binder.toolbar.tvtitle.invisible()
        binder.toolbar.ivdot.invisible()
        binder.toolbar.ivback.setOnClickListener {
            baseActivity.onBackPressed()
        }
    }

    private fun isValidation(): Boolean {
        binder.emaillayout.error = null
        binder.namelayout.error = null
        binder.textarealayout.error = null

        if (binder.etusername.text?.trim().toString().isEmpty()) {
            binder.namelayout.error = baseActivity.getString(R.string.v_validname)
            baseActivity.showtoast(baseActivity.getString(R.string.v_validname))
            return false
        }
        if (binder.etemail.text?.trim().toString().isEmpty()) {
            binder.emaillayout.error = baseActivity.getString(R.string.v_emailvalidation)
            baseActivity.showtoast(baseActivity.getString(R.string.v_emailvalidation))
            return false
        }

        if (!isEmailValid(binder.etemail.text?.trim().toString())) {
            binder.emaillayout.error = baseActivity.getString(R.string.v_validemail)
            baseActivity.showtoast(baseActivity.getString(R.string.v_validemail))
            return false
        }
        if (binder.ettextarea.text?.trim().toString().isEmpty()) {
            binder.textarealayout.error = baseActivity.getString(R.string.v_validtestfield)
            baseActivity.showtoast(baseActivity.getString(R.string.v_validtestfield))
            return false
        }
        return true

    }


}