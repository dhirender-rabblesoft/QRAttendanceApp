package com.app.qrcodescanner.adapter

import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.BaseAdapter
import com.app.qrcodescanner.base.KotlinBaseActivity

class AttendanceListingAdapter(val baseActivity: KotlinBaseActivity, val itemClick: (Int) -> Unit) :
    BaseAdapter<String>(R.layout.item_checkin_out_list) {
    override fun onBindViewHolder(holder: IViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10

    }
}