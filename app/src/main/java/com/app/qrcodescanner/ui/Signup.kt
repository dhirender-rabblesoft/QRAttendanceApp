package com.app.qrcodescanner.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityLoginBinding
import com.app.qrcodescanner.viewmodel.LoginViewModel

class Signup : KotlinBaseActivity()
{
    lateinit var binding: ActivityLoginBinding
    lateinit var viewmodel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewmodel = ViewModelProvider(this).get(LoginViewModel::class.java)

         viewmodel.setBinder(binding, this,"1")


    }


}