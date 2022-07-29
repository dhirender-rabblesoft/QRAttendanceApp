package com.app.qrcodescanner.ui.timesheet

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import com.app.qrcodescanner.R
import com.app.qrcodescanner.adapter.AutoSuggestAdapter
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.gone
import com.app.qrcodescanner.extension.isNotNull
import com.app.qrcodescanner.extension.visible
import com.app.qrcodescanner.ui.Signatures
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_time_sheet.*
import kotlinx.android.synthetic.main.common_toolbar.*
import java.io.File


class TimeSheet : KotlinBaseActivity()
{
    private val TRIGGER_AUTO_COMPLETE = 100
    private val AUTO_COMPLETE_DELAY: Long = 300
    private var handler: Handler? = null
    private var autoSuggestAdapter: AutoSuggestAdapter? = null
    var companynamelist=ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_sheet)
        setadapter()
        settoolbar()
    }
    companion object{
        var signature:File?=null
    }

    override fun onResume() {
        super.onResume()
        if (signature.isNotNull())
        {
            ivsignature.visible()
            tvsign.gone()
            Log.e("fileeee", signature!!.path)
            Picasso.get().load(signature!!).into(ivsignature)
        }
    }
    private  fun settoolbar()
    {
        ivdot.gone()
        ivback.setOnClickListener {
            onBackPressed()
        }
        llsign.setOnClickListener {
            openA(Signatures::class)
        }
    }
    private  fun setadapter()
    {
        companynamelist.add("Test 1")
        companynamelist.add("Test 2")
        //Setting up the adapter for AutoSuggest
        //Setting up the adapter for AutoSuggest
        autoSuggestAdapter = AutoSuggestAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line
        )
        clientauto.setThreshold(2)
        clientauto.setAdapter(autoSuggestAdapter)
        clientauto.setOnItemClickListener(
            OnItemClickListener { parent, view, position, id ->

            })
        clientauto.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
                handler!!.removeMessages(TRIGGER_AUTO_COMPLETE)
                handler?.sendEmptyMessageDelayed(
                    TRIGGER_AUTO_COMPLETE,
                    AUTO_COMPLETE_DELAY
                )
            }

            override fun afterTextChanged(s: Editable) {}
        })

        autoSuggestAdapter?.setData(companynamelist);
        autoSuggestAdapter?.notifyDataSetChanged();

        handler = Handler { msg ->
            if (msg.what === TRIGGER_AUTO_COMPLETE) {
                if (!TextUtils.isEmpty(clientauto.getText())) {

                }
            }
            false
        }
    }
}