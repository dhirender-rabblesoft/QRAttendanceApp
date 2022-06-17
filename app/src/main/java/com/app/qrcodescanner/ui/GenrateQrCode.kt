package com.app.qrcodescanner.ui

import android.Manifest
import android.os.Bundle
import android.util.Log
import com.app.qrcodescanner.R
import com.app.qrcodescanner.adapter.QrCodeListingAdapter
import com.app.qrcodescanner.applications.QrApplication
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.gone
import com.app.qrcodescanner.extension.isNotNull
import com.app.qrcodescanner.model.LoginJson
import com.app.qrcodescanner.model.QrCodeListingModel
import com.app.qrcodescanner.reposiory.CommonRepository
import com.app.qrcodescanner.utils.Keys
import com.app.qrcodescanner.utils.SharedPreferenceManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.activity_genrate_qr_code.*
import kotlinx.android.synthetic.main.common_toolbar.*
import kotlinx.android.synthetic.main.side_menu_bar.view.*

class GenrateQrCode : KotlinBaseActivity() {
    var qrCodeListing = ArrayList<QrCodeListingModel.Data.Data>()
    var commonRepository = CommonRepository(QrApplication.myApp!!)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genrate_qr_code)
        parsedata()
        setToolbar()
        setQrCodeListingApi()

    }

    private fun setToolbar() {
        ivback.gone()
        tvtitle.setText("Welcome Admin")
        ivback.setImageResource(R.drawable.logout)
        ivdot.setOnClickListener {
            token=""
            SharedPreferenceManager(this).saveString(Keys.USERDATA,"")
            SharedPreferenceManager(this).saveString(Keys.USERID,"")
             openA(LoginActivity::class)
            finishAffinity()
         }
    }

    private fun setQrCodeListingAdapter() {
        val qrCodeListingAdapter = QrCodeListingAdapter(this) {

        }
        rvRecentListAdapter.adapter = qrCodeListingAdapter
        qrCodeListingAdapter.addNewList(qrCodeListing)
//        rvRecentListAdapter
    }


    fun parsedata() {
        val data = SharedPreferenceManager(this).getString(Keys.USERDATA).toString()
        val gson = Gson()
        userdata = gson.fromJson(data, LoginJson::class.java)
        setdata()
        if (token.isEmpty()) {
            token = "Bearer " + SharedPreferenceManager(this).getString(Keys.TOKEN).toString()
            Log.e("token", token)
        }

    }


    private fun setdata() {
        if (userdata?.data?.user?.image.isNotNull()) {
            Glide.with(this).load(userdata?.data?.user?.image)
                .diskCacheStrategy(
                    DiskCacheStrategy.NONE
                )
                .skipMemoryCache(true).into(imageView1)

        }
        tvusername.text =
            userdata?.data?.user?.first_name + " " + userdata?.data?.user?.last_name
        tvuserdesignation.text = userdata?.data?.user?.role

    }


    private fun setQrCodeListingApi() {
        commonRepository.qrCodeListing(this, token, Keys.QR_CLIENT_LISTING_END_POINT) {
            qrCodeListing.addAll(it.data.data)
            setQrCodeListingAdapter()
        }
    }


    companion object {
        var userdata: LoginJson? = null
        var token = ""
    }

}