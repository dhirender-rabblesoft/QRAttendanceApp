package com.app.qrcodescanner.adapter

import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.BaseAdapter
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.isNotNull
import com.app.qrcodescanner.model.FeeedbackListJson
import com.app.qrcodescanner.utils.Utils
import kotlinx.android.synthetic.main.item_feedvack.view.*


class FeedbackAdapter(val baseActivity: KotlinBaseActivity, val itemClick: (Int) -> Unit) :
    BaseAdapter<FeeedbackListJson.Data.Data>(R.layout.item_feedvack) {
    override fun onBindViewHolder(holder: IViewHolder, position: Int) {
        holder.itemView.apply {
            tvdate.text=Utils.formateDateFromstring(Utils.DATETIMEFORMAT,Utils.DATEFORMAT,list[position].created_at)
            if (list[position].client.isNotNull())
            {
                tvclientname.text=list[position].client.name
            }
            if (list[position].authorized_by.isNotNull())
            {
                tvauthname.text=list[position].authorized_by.name
            }
            tvdesc.text=list[position].comment
            tvinvoicestatus.setOnClickListener {
                itemClick(position)
            }
            when(list[position].rating.toInt().toString())
            {
                "5"->{
                    tvreviewvaleu.text="(5)"+" Excellent"
                    rating.rating= 5.0F
                }
                "4"->{
                    rating.rating= 4.0F
                    tvreviewvaleu.text="(4)"+" Very Good"

                }
                "3"->{
                    rating.rating= 3.0F
                    tvreviewvaleu.text="(3)"+" Good"

                }
                "2"->{
                    rating.rating= 2.0F
                    tvreviewvaleu.text="(2)"+" Weak"

                }
                "1"->{
                    rating.rating= 1.0F
                    tvreviewvaleu.text="(1)"+" Poor"

                }
            }



        }

    }




}