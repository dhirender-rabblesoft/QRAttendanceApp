package com.app.qrcodescanner.adapter
import android.os.Bundle
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.BaseAdapter
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.isNotNull

import com.app.qrcodescanner.model.AttandanceListing
import com.app.qrcodescanner.model.TimeSheetListJson
import com.app.qrcodescanner.model.TimelistJson
import com.app.qrcodescanner.ui.feedback.FeedBack
import com.app.qrcodescanner.ui.timesheet.TimeSheet
import com.app.qrcodescanner.utils.Utils
import kotlinx.android.synthetic.main.item_timesheet.view.*
class TimesheetListingAdapter(val baseActivity: KotlinBaseActivity, val itemClick: (Int) -> Unit) :
    BaseAdapter<TimeSheetListJson.Data.Data>(R.layout.item_timesheet)
{
    override fun onBindViewHolder(holder: IViewHolder, position: Int)
    {
        holder.itemView.apply {
           if (list[position].client.isNotNull())
           {
               tvclientname.text=list[position].client.name
           }
            if (list[position].authorized_by.isNotNull())
           {
               tvauthname.text=list[position].authorized_by.name
               tvinvoicestatus.setOnClickListener {
                   val bund= Bundle()
                   bund.putString("punch_in",list[position].punch_in)
                   baseActivity.openA(FeedBack::class,bund)
               }
           }
            else
            {
                tvinvoicestatus.text="Fill Timesheet"
                tvinvoicestatus.setOnClickListener {
                    val bund= Bundle()
                    bund.putString("punch_in",list[position].punch_in)
                    baseActivity.openA(TimeSheet::class,bund)
                }
            }
            tvdate.text=Utils.formateDateFromstring(Utils.DATETIMEFORMAT, Utils.DATEFORMAT,list[position].punch_in)

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


}