package com.app.qrcodescanner.fragement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.base.KotlinBaseFragment
import com.app.qrcodescanner.databinding.FragmentInvoiceDetailFragementBinding
import com.app.qrcodescanner.viewmodel.InvoiceDetailFragmentViewModel
import kotlinx.android.synthetic.main.fragment_invoice_detail_fragement.*

class InvoiceDetailFragement(val baseActivity: KotlinBaseActivity) : KotlinBaseFragment() {
    lateinit var binding: FragmentInvoiceDetailFragementBinding
    lateinit var viewmodel: InvoiceDetailFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_invoice_detail_fragement,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel = ViewModelProvider(this).get(InvoiceDetailFragmentViewModel::class.java)
        viewmodel.setBinder(binding,baseActivity)




    }


}