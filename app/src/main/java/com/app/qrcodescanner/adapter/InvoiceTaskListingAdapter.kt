package com.app.qrcodescanner.adapter

import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.BaseAdapter
import com.app.qrcodescanner.base.KotlinBaseActivity
import kotlinx.android.synthetic.main.common_toolbar.view.*
import kotlinx.android.synthetic.main.item_invoice.view.*

class InvoiceTaskListingAdapter(val baseActivity: KotlinBaseActivity, val itemClick: (Int) -> Unit) :
    BaseAdapter<String>(
        R.layout.item_invoice_tasklisting
    ) {
    override fun onBindViewHolder(holder: IViewHolder, position: Int) {
        holder.itemView.apply {

        }
    }

    override fun getItemCount(): Int {
        return 5
    }
}