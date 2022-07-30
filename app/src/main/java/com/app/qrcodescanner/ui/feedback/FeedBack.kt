package com.app.qrcodescanner.ui.feedback

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.gone
import com.app.qrcodescanner.extension.isNotNull
import com.app.qrcodescanner.extension.visible
import com.app.qrcodescanner.ui.Signatures
import com.app.qrcodescanner.utils.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_feed_back.*
import kotlinx.android.synthetic.main.common_toolbar.*
import java.io.File

class FeedBack : KotlinBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_back)
        settoolbar()
        setclicks()
    }
    private  fun settoolbar()
    {
        tvtitle.text="Feedback"
        ivdot.gone()
        ivback.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        if (signature.isNotNull())
        {
            clearsign.visible()
            ivsignature.visible()
            tvsign.gone()
            Picasso.get().load(signature!!).into(ivsignature)

        }
    }
    private  fun  validations():Boolean
    {
        commentlay.error=null
        addresslay.error=null
        passcodelay.error=null
        postionlay.error=null
        namelay.error=null
        if (etcomment.text.toString().trim().isEmpty())
        {
            commentlay.error="Please enter comments"
            return false
        }
        if (etaddress.text.toString().trim().isEmpty())
        {
            addresslay.error="Please enter address"
            return false
        }
        if (etpostcode.text.toString().trim().isEmpty())
        {
            passcodelay.error="Please enter postcode"
            return false
        }
        if (etnurse.text.toString().trim().isEmpty())
        {
            namelay.error="Please enter name"
            return false
        }
        if (etpos.text.toString().trim().isEmpty())
        {
            postionlay.error="Please enter position"
            return false
        }
        if (signature==null)
        {
            showtoast("Please sign")
             return false
        }
        if (tvdate.text.toString().isEmpty())
        {
            showtoast("Please select date")
             return false
        }
        if (rbgrp.checkedRadioButtonId==-1)
        {
            showtoast("Please select review")
             return false
        }
        return true
    }
    companion object
    {
        var signature: File?=null
    }
    private  fun setclicks()
    {
        loginbutton.setOnClickListener {
            if (validations())
            {

            }
        }
        tvdate.setOnClickListener {
            Utils.shoedatepicker(this,tvdate,true){

            }
        }
        clearsign.setOnClickListener {
            clearsign.gone()
            ivsignature.gone()
            tvsign.visible()
            signature=null
        }
        llsign.setOnClickListener {
            val  bund=Bundle()
            bund.putString("from","1")
            openA(Signatures::class,bund)
        }
    }
}