package com.app.qrcodescanner.ui

 import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivitySignupBinding
import com.app.qrcodescanner.viewmodel.SingupViewModel

class SignupActivity : KotlinBaseActivity() {
    lateinit var binding: ActivitySignupBinding
    lateinit var viewmodel: SingupViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_signup)
        viewmodel = ViewModelProvider(this).get(SingupViewModel::class.java)
        viewmodel.setBinder(binding,this)

    }
}