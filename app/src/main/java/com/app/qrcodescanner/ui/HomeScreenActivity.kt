package com.app.qrcodescanner.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityHomeScreenBinding
import com.app.qrcodescanner.viewmodel.HomeScreenViewModel
import kotlinx.android.synthetic.main.activity_otpverify.*

class HomeScreenActivity : KotlinBaseActivity() {
    lateinit var binding: ActivityHomeScreenBinding
    lateinit var viewModel: HomeScreenViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_screen)
        viewModel = ViewModelProvider(this).get(HomeScreenViewModel::class.java)
        viewModel.setBinder(binding, this)
    }
}