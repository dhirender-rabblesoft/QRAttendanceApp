package com.app.qrcodescanner.adapter

import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.BaseAdapter
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.visible
import com.app.qrcodescanner.ui.AttendanceListingScreen
import kotlinx.android.synthetic.main.item_checkin_out_list.view.*

class AttendanceHomeListingAdapter(
    val baseActivity: KotlinBaseActivity,
    val itemClick: (Int) -> Unit
) :
    BaseAdapter<String>(R.layout.item_checkin_out_list) {
    override fun onBindViewHolder(holder: IViewHolder, position: Int) {


        holder.itemView.apply {
            if (position == 4) {
                clmorecontainer.visible()
                morebutton.setOnClickListener {
                    baseActivity.openA(AttendanceListingScreen::class)
                }
            }

        }


    }

    override fun getItemCount(): Int {
        return 5

    }
}