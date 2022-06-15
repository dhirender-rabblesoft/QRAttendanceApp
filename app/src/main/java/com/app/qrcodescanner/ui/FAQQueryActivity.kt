package com.app.qrcodescanner.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityFaqqueryBinding
import com.app.qrcodescanner.viewmodel.FAQQueryViewModel

class FAQQueryActivity : KotlinBaseActivity() {
    lateinit var binding: ActivityFaqqueryBinding
    lateinit var viewmodel: FAQQueryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_faqquery)
        viewmodel = ViewModelProvider(this).get(FAQQueryViewModel::class.java)
        viewmodel.setBinder(binding, this)

    }
}