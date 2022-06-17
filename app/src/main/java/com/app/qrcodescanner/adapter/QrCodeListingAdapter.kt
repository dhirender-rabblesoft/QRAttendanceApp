package com.app.qrcodescanner.adapter

import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.BaseAdapter
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.visible
import com.app.qrcodescanner.model.QrCodeListingModel
import com.app.qrcodescanner.ui.AttendanceListingScreen
import kotlinx.android.synthetic.main.item_checkin_out_list.view.*

class QrCodeListingAdapter(
    val baseActivity: KotlinBaseActivity,
    val itemClick: (Int) -> Unit
) :
    BaseAdapter<QrCodeListingModel.Data.Data>(R.layout.item_qr_code_list) {
    override fun onBindViewHolder(holder: IViewHolder, position: Int) {


        holder.itemView.apply {
            tvdate.setText(list[position].title)
            tvcheckintime.setText(list[position].title)

        }


    }


}