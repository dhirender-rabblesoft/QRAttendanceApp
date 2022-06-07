package com.app.qrcodescanner.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.AppViewModel
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityEditProfileBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import java.io.File

class EditProfileViewModel(application: Application) : AppViewModel(application) {
    private lateinit var binder: ActivityEditProfileBinding
    private lateinit var mContext: Context
    private lateinit var baseActivity: KotlinBaseActivity
    var file: File? = null
    fun setBinder(binding: ActivityEditProfileBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binding
        this.mContext = binding.root.context
        this.baseActivity = baseActivity
        setClick()
    }

    private fun setClick() {
        binder.ivBack.setOnClickListener {
            baseActivity.onBackPressed()
        }
        binder.loginbutton.setOnClickListener {
            if (validation()) {
                baseActivity.showtoast("Button is Clicked")
            }
        }

    }


    private fun validation(): Boolean {
        binder.namelayout.error = null
        binder.phonenumberwrap.error = null
        if (binder.etusername.text?.trim().toString().isEmpty()) {
            binder.namelayout.error = baseActivity.getString(R.string.v_validname)
            baseActivity.showtoast(baseActivity.getString(R.string.v_validname))
            return false
        }
        if (binder.etphonenumber.text?.trim().toString().isEmpty()) {
            binder.phonenumberwrap.error = baseActivity.getString(R.string.v_validphone)
            baseActivity.showtoast(binder.etphonenumber.text?.trim().toString())
            return false
        }
        return true
    }

    fun setfile(file: File) {
        this.file = file
        Glide.with(baseActivity).load(file).diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true).into(binder.imageView1)

    }


}