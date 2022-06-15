package com.app.qrcodescanner.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityFaqactivityBinding
import com.app.qrcodescanner.viewmodel.FAQViewModel

class FAQActivity : KotlinBaseActivity() {
    lateinit var binding: ActivityFaqactivityBinding
    lateinit var viewmodel: FAQViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_faqactivity)
        viewmodel = ViewModelProvider(this).get(FAQViewModel::class.java)
        viewmodel.setBinder(binding, this)

    }
}