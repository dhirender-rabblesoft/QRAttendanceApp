package com.app.qrcodescanner.adapter

import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.BaseAdapter
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.gone
import com.app.qrcodescanner.extension.isNotNull
import com.app.qrcodescanner.extension.visible
import com.app.qrcodescanner.model.AttandanceListing
import com.app.qrcodescanner.ui.AttendanceListingScreen
import com.app.qrcodescanner.utils.Utils
import kotlinx.android.synthetic.main.item_checkin_out_list.view.*

class AttendanceHomeListingAdapter(
    val baseActivity: KotlinBaseActivity,
    val itemClick: (Int) -> Unit
) :
    BaseAdapter<AttandanceListing.Data>(R.layout.item_checkin_out_list) {
    override fun onBindViewHolder(holder: IViewHolder, position: Int) {


        holder.itemView.apply {

            tvdate.text= Utils.formateDateFromstring(Utils.DATETIMEFORMAT, Utils.DATEFORMAT,list[position].punch_in)
            tvcheckintime.text= Utils.formateDateFromstring(Utils.DATETIMEFORMAT, Utils.TIMEFORMAT,list[position].punch_in)
            if (list[position].punch_out.isNotNull())
            {
                tvcheckouttime.visible()
                tvcheckouttime.text=
                    Utils.formateDateFromstring(Utils.DATETIMEFORMAT, Utils.TIMEFORMAT,list[position].punch_out)
            }
            else{
                tvcheckouttime.visible()
                tvcheckouttime.text="N/A"
            }
            if (position == 4) {
                clmorecontainer.visible()
                morebutton.setOnClickListener {
                    baseActivity.openA(AttendanceListingScreen::class)
                }
            }

        }


    }

}