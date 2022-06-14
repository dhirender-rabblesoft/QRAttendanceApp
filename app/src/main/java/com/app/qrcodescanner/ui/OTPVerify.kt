package com.app.qrcodescanner.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityOtpverifyBinding
import com.app.qrcodescanner.viewmodel.OTPVerifyViewModel

class OTPVerify : KotlinBaseActivity() {
    lateinit var binding: ActivityOtpverifyBinding
    lateinit var viewmodel: OTPVerifyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otpverify)
        viewmodel = ViewModelProvider(this).get(OTPVerifyViewModel::class.java)
        viewmodel.setBinder(binding, this)
    }
}