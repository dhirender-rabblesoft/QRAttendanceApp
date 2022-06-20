package com.app.qrcodescanner.adapter

import com.airbnb.lottie.utils.Utils
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.BaseAdapter
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.isNotNull
import com.app.qrcodescanner.model.InvoiceListJson
import com.app.qrcodescanner.utils.Keys
import kotlinx.android.synthetic.main.common_toolbar.view.*
import kotlinx.android.synthetic.main.item_invoice.view.*

class InvoiceListingAdapter(val baseActivity: KotlinBaseActivity, val itemClick: (Int) -> Unit) :
    BaseAdapter<InvoiceListJson.Data>(
        R.layout.item_invoice
    ) {
    override fun onBindViewHolder(holder: IViewHolder, position: Int) {
        holder.itemView.apply {
            tvinvoiceId.text=list[position].invoice_number
            if (list[position].created_at.isNotNull())
            {
                tvdate.text=list[position].created_at
            }
            cvContainer.setOnClickListener {
                itemClick(position)
            }
            tvinvoiceamount.text="$"+list[position].price.toString()
            if (list[position].status.equals(1)){
                tvinvoicestatus.background = baseActivity.resources.getDrawable(R.drawable.green_rectangle_round_background)
                tvinvoicestatus.setText("Paid")
            }

        }

    }


}