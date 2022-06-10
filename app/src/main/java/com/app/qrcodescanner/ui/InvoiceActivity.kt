package com.app.qrcodescanner.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityInvoiceBinding
import com.app.qrcodescanner.viewmodel.InvoiceViewModel

class InvoiceActivity : KotlinBaseActivity() {
    lateinit var binding: ActivityInvoiceBinding
    lateinit var viewModel: InvoiceViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_invoice)
        viewModel = ViewModelProvider(this).get(InvoiceViewModel::class.java)
        viewModel.setBinder(binding, this)

    }

}