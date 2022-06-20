package com.app.qrcodescanner.viewmodel

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.qrcodescanner.adapter.InvoiceListingAdapter
import com.app.qrcodescanner.base.AppViewModel
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityInvoiceBinding
import com.app.qrcodescanner.extension.gone
import com.app.qrcodescanner.extension.invisible
import com.app.qrcodescanner.fragement.InvoiceDetailFragement
import com.app.qrcodescanner.model.InvoiceListJson
import com.app.qrcodescanner.reposiory.CommonRepository
import com.app.qrcodescanner.ui.HomeScreenActivity
import com.app.qrcodescanner.utils.Keys
import kotlinx.android.synthetic.main.activity_edit_profile.view.*
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_login.view.tvtitle
import kotlinx.android.synthetic.main.common_toolbar.view.*

class InvoiceViewModel(application: Application) : AppViewModel(application)
{
    private lateinit var binder: ActivityInvoiceBinding
    private lateinit var baseActivity: KotlinBaseActivity
    private lateinit var mContext: Context
    var commonRepository=CommonRepository(application)
    fun setBinder(binding: ActivityInvoiceBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binding
        this.mContext = binding.root.context
        this.baseActivity = baseActivity
        setToolbar()
         invoicelisting()

    }
    private  fun  invoicelisting()
    {
        commonRepository.invoicelisting(baseActivity,HomeScreenActivity.token,Keys.INVOICELISTING){
            setInvoiceListingAdapter(it.data as ArrayList<InvoiceListJson.Data>)
        }
    }

    private fun setInvoiceDetialFrgement(  invoiceListJson: InvoiceListJson.Data) {
        val fragement = InvoiceDetailFragement(baseActivity,invoiceListJson)
        val fragmentManager = baseActivity.supportFragmentManager.beginTransaction()
        fragmentManager.add(binder.fragmentContainer.id, fragement)
            .addToBackStack(null)
            .commit()
    }

    private fun setToolbar() {
        binder.toolbar.tvtitle.setText("Invoice")
        binder.toolbar.ivdot.invisible()
        binder.toolbar.ivback.setOnClickListener {
            baseActivity.onBackPressed()
        }
    }

    private fun setInvoiceListingAdapter(list:ArrayList<InvoiceListJson.Data>) {
        val layoutmanger = LinearLayoutManager(baseActivity, LinearLayoutManager.VERTICAL, false)
        binder.rvInvoicelisting.layoutManager = layoutmanger
        val invoiceListing = InvoiceListingAdapter(baseActivity) {
            setInvoiceDetialFrgement(list[it])

        }
        invoiceListing.addNewList(list)
        binder.rvInvoicelisting.adapter = invoiceListing

    }
}