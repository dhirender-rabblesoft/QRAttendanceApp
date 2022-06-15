package com.app.qrcodescanner.adapter

import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.BaseAdapter
import com.app.qrcodescanner.base.KotlinBaseActivity
import kotlinx.android.synthetic.main.common_toolbar.view.*
import kotlinx.android.synthetic.main.item_invoice.view.*

class InvoiceListingAdapter(val baseActivity: KotlinBaseActivity, val itemClick: (Int) -> Unit) :
    BaseAdapter<String>(
        R.layout.item_invoice
    ) {
    override fun onBindViewHolder(holder: IViewHolder, position: Int) {
        holder.itemView.apply {
            cvContainer.setOnClickListener {
                itemClick(position)
            }
            if (position/2 ==0){
                tvinvoicestatus.background = baseActivity.resources.getDrawable(R.drawable.green_rectangle_round_background)
                tvinvoicestatus.setText("Paid")
            }

        }



    }

    override fun getItemCount(): Int {
        return 20
    }
}