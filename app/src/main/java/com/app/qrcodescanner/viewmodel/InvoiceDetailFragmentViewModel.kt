package com.app.qrcodescanner.viewmodel

import android.app.Application
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.qrcodescanner.adapter.InvoiceTaskListingAdapter
import com.app.qrcodescanner.base.AppViewModel
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.FragmentInvoiceDetailFragementBinding

class InvoiceDetailFragmentViewModel(application: Application) : AppViewModel(application) {
    private lateinit var binder: FragmentInvoiceDetailFragementBinding
    private lateinit var mContext: Context
    private lateinit var baseActivity: KotlinBaseActivity
    fun setBinder(
        fragmentInvoiceDetailFragementBinding: FragmentInvoiceDetailFragementBinding,
        baseActivity: KotlinBaseActivity
    ) {
        this.binder = fragmentInvoiceDetailFragementBinding
        this.mContext = fragmentInvoiceDetailFragementBinding.root.context
        this.baseActivity = baseActivity
//        donwloadInvoice()
        setInvoiceTaskAdapter()
    }

    private fun setInvoiceTaskAdapter() {
        val linearLayoutManager =
            LinearLayoutManager(baseActivity, LinearLayoutManager.VERTICAL, false)
        binder.rvinvoicelist.layoutManager = linearLayoutManager
        val invoiceTaskListingAdapter = InvoiceTaskListingAdapter(baseActivity) {

        }
        binder.rvinvoicelist.adapter = invoiceTaskListingAdapter


    }

//    private fun donwloadInvoice() {
//        binder.loginbutton.setOnClickListener {
//            val url = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"
//            baseActivity.download(url)
//        }
//    }
}