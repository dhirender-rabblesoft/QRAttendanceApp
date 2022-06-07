package com.app.qrcodescanner.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityAttendanceListingScreenBinding
import com.app.qrcodescanner.viewmodel.AttendanceListingViewModel
import kotlinx.android.synthetic.main.activity_otpverify.*

class AttendanceListingScreen : KotlinBaseActivity() {
    lateinit var binding: ActivityAttendanceListingScreenBinding
    lateinit var viewmodel: AttendanceListingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_attendance_listing_screen)
        viewmodel = ViewModelProvider(this).get(AttendanceListingViewModel::class.java)
        viewmodel.setBinder(binding, this)

    }
}