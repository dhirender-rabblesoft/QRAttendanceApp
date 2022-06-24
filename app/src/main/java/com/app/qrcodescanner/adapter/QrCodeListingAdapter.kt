package com.app.qrcodescanner.adapter

import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.BaseAdapter
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.isNotNull
import com.app.qrcodescanner.extension.visible
import com.app.qrcodescanner.model.QrCodeListingModel
import com.app.qrcodescanner.ui.AttendanceListingScreen
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.item_checkin_out_list.view.*
import kotlinx.android.synthetic.main.item_checkin_out_list.view.tvcheckintime
import kotlinx.android.synthetic.main.item_checkin_out_list.view.tvdate
import kotlinx.android.synthetic.main.item_qr_code_list.view.*

class QrCodeListingAdapter(
    val baseActivity: KotlinBaseActivity,
    val itemClick: (Int,Int) -> Unit
) :
    BaseAdapter<QrCodeListingModel.Data.Data>(R.layout.item_qr_code_list) {
    override fun onBindViewHolder(holder: IViewHolder, position: Int) {

        holder.itemView.apply {
            Glide.with(baseActivity).load(list[position].qrcode).
            diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.codeplaceholder)
                .skipMemoryCache(true).into(iv_qrCode)
            if (list[position].cilent.isNotNull()){
                tvdate.setText(list[position].cilent.name)

            }
            if (list[position].status.equals(1))
            {
                ivcheck2.isChecked = true
            }
            else{
                ivcheck2.isChecked = false
            }
            tvcheckintime.setText(list[position].title)
            ivcheck2.setOnCheckedChangeListener { switch, isChecked ->
                // Handle switch checked/unchecked
                if (isChecked){
                    itemClick(position,1)
                }else {
                    itemClick(position,2)
                }
            }

        }


    }


}