package com.app.qrcodescanner.ui

 import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityCreatePasswordBinding
import com.app.qrcodescanner.viewmodel.CreatePasswordViewModel

class CreatePassword : KotlinBaseActivity() {
    lateinit var binding: ActivityCreatePasswordBinding
    lateinit var viewmodel: CreatePasswordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_password)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_password)
        viewmodel = ViewModelProvider(this).get(CreatePasswordViewModel::class.java)
        viewmodel.setBinder(binding, this)
    }


}