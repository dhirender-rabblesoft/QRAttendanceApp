package com.app.qrcodescanner.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.gone
import kotlinx.android.synthetic.main.activity_add_ar_code.*
import kotlinx.android.synthetic.main.common_toolbar.*

class AddArCode : KotlinBaseActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ar_code)
        settoolbar()
        setTimeAdapter()
    }
    private fun setTimeAdapter() {
        val foodtimearrays = resources.getStringArray(R.array.food_time_array)
        var timeadapter = ArrayAdapter(this, R.layout.dropdown_item, foodtimearrays)
        ettime.setAdapter(timeadapter)
    }
    private  fun settoolbar()
    {
        ivdot.gone()
        tvtitle.text="Add Address"
        ivback.setOnClickListener {
            onBackPressed()
        }

    }

}