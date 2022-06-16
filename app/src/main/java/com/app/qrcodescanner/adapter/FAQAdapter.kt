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
import com.app.qrcodescanner.utils.CustomTextView
import kotlinx.android.synthetic.main.item_faq.view.*

class FAQAdapter(val baseActivity: KotlinBaseActivity, val itemClick: (Int) -> Unit) :
    BaseAdapter<FaqJson.Data>(R.layout.item_faq) {
    var isflag:Boolean = true
    override fun onBindViewHolder(holder: IViewHolder, position: Int) {


        holder.itemView.apply {
            tvfaqtitle.setText(list[position].question)
            tvdes.setText(list[position].answer)
            if (position == 0 ){
                tvfaqtitle.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_down_24,0)
                clanserconatiner.visible()
                cvfaqcontainer.cardElevation = 10f
                cvfaqcontainer.strokeWidth = 0
            }

            tvfaqtitle.setDrawableRightClickListener(object :
                CustomTextView.DrawableRightClickListener {
                override fun onDrawableRightClickListener(view: View?) {
                        if (isflag){
                            isflag = false
                            tvfaqtitle.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_down_24,0)
                            clanserconatiner.visible()
                            cvfaqcontainer.cardElevation = 10f
                            cvfaqcontainer.strokeWidth = 0
                            Log.e("conatnervisisable","858585858585")


                        } else {
                            cvfaqcontainer.cardElevation = 0f
                            cvfaqcontainer.strokeWidth = 4
                            cvfaqcontainer.strokeWidth = getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._1sdp);

                            cvfaqcontainer.strokeColor = baseActivity.resources.getColor(R.color.black_4)
                            isflag = true
                            tvfaqtitle.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_up_24,0)
                            clanserconatiner.gone()

                            Log.e("conatnervisisable","15151515151515")
                        }

                }
            })
        }

    }


}