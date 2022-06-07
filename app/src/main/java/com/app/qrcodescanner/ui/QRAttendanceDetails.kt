package com.app.qrcodescanner.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityQrattendanceDetailsBinding
import com.app.qrcodescanner.viewmodel.QRAttendanceDetailViewModel
import kotlinx.android.synthetic.main.activity_otpverify.*

class QRAttendanceDetails : KotlinBaseActivity() {

    lateinit var binding:ActivityQrattendanceDetailsBinding
    lateinit var viewmodel :QRAttendanceDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_qrattendance_details)
        viewmodel = ViewModelProvider(this).get(QRAttendanceDetailViewModel::class.java)
        viewmodel.setBinder(binding,this)
    }
}