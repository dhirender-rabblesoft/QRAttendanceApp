package com.app.qrcodescanner.ui

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityHomeScreenBinding
import com.app.qrcodescanner.extension.isNotNull
import com.app.qrcodescanner.viewmodel.HomeScreenViewModel
import com.example.easywaylocation.EasyWayLocation
import com.example.easywaylocation.Listener
import kotlinx.android.synthetic.main.activity_otpverify.*

class HomeScreenActivity : KotlinBaseActivity() {
    lateinit var binding: ActivityHomeScreenBinding
    lateinit var viewModel: HomeScreenViewModel
    lateinit var location: EasyWayLocation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_screen)
        viewModel = ViewModelProvider(this).get(HomeScreenViewModel::class.java)
        viewModel.setBinder(binding, this)
    }

    override fun onResume() {
        super.onResume()

    }


}