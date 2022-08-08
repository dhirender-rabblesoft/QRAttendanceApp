package com.app.qrcodescanner.adapter

import android.util.Log
import android.view.View
import androidx.annotation.BoolRes
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.BaseAdapter
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.gone
import com.app.qrcodescanner.extension.visible
import com.app.qrcodescanner.model.FaqJson
import com.app.qrcodescanner.model.FeebackListJson
import com.app.qrcodescanner.model.FeedbackDetailJson
import com.app.qrcodescanner.utils.CustomTextView
import kotlinx.android.synthetic.main.item_faq.view.*
import kotlinx.android.synthetic.main.item_selection.view.*

class ViewSelectionAdapter(val baseActivity: KotlinBaseActivity, val itemClick: (Int) -> Unit) :
    BaseAdapter<FeebackListJson.Data>(R.layout.item_selection) {
    var isflag:Boolean = true
    override fun onBindViewHolder(holder: IViewHolder, position: Int) {


        holder.itemView.apply {
            if (list[position].isfeedback)
            {

                ivpdf.setImageResource(R.drawable.check)
            }
            else{

                ivpdf.setImageResource(R.drawable.uncheck)
            }
            tvtitle.text=list[position].feedback


        }

    }


}