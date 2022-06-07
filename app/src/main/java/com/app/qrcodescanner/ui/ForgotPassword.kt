package com.app.qrcodescanner.ui

 import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityForgotPasswordBinding
import com.app.qrcodescanner.viewmodel.ForgotPassowordViewModel

class ForgotPassword : KotlinBaseActivity() {
    lateinit var binding: ActivityForgotPasswordBinding
    lateinit var viewmodel: ForgotPassowordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)
        viewmodel = ViewModelProvider(this).get(ForgotPassowordViewModel::class.java)
        viewmodel.setBinder(binding, this)
    }
}