package com.app.qrcodescanner.viewmodel

import android.app.Application
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.qrcodescanner.adapter.FAQAdapter
import com.app.qrcodescanner.base.AppViewModel
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityFaqactivityBinding
import com.app.qrcodescanner.extension.invisible
import com.app.qrcodescanner.ui.FAQQueryActivity
import com.app.qrcodescanner.utils.Utils
import kotlinx.android.synthetic.main.activity_faqactivity.view.*
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_login.view.tvtitle
import kotlinx.android.synthetic.main.common_search_bar.view.*
import kotlinx.android.synthetic.main.common_toolbar.view.*

class FAQViewModel(application: Application) : AppViewModel(application) {
    private lateinit var binder: ActivityFaqactivityBinding
    private lateinit var mContext: Context
    private lateinit var baseActivity: KotlinBaseActivity
    lateinit var list: ArrayList<String>
    lateinit var faqadapter: FAQAdapter
    fun setBinder(binding: ActivityFaqactivityBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binding
        this.mContext = binding.root.context
        this.baseActivity = baseActivity
        settoolbar()
        setFaqAdapter()
        setClick()
        searchbar()
    }

    private fun settoolbar() {
        binder.toolbar.tvtitle.invisible()
        binder.toolbar.ivdot.invisible()
        binder.toolbar.ivback.setOnClickListener {
            baseActivity.onBackPressed()
        }
    }

    private fun setFaqAdapter() {
        list = ArrayList<String>()
        list.add("How to become a billinor and how to get your money back after three days before of the month")
        list.add("How to become a billinor and how to get your money back after three days before of the month")
        list.add("Who become a billinor and how to get your money back after three days before of the month")
        list.add("Who become a billinor and how to get your money back after three days before of the month")
        list.add("We become a billinor and how to get your money back after three days before of the month")
        list.add("We become a billinor and how to get your money back after three days before of the month")
        list.add("We become a billinor and how to get your money back after three days before of the month")
        list.add("We become a billinor and how to get your money back after three days before of the month")
        list.add("We become a billinor and how to get your money back after three days before of the month")
        list.add("We become a billinor and how to get your money back after three days before of the month")
        list.add("She become a billinor and how to get your money back after three days before of the month")
        list.add("She become a billinor and how to get your money back after three days before of the month")
        list.add("She become a billinor and how to get your money back after three days before of the month")
        list.add("She become a billinor and how to get your money back after three days before of the month")
        list.add("She become a billinor and how to get your money back after three days before of the month")
        list.add("HE become a billinor and how to get your money back after three days before of the month")
        list.add("HE become a billinor and how to get your money back after three days before of the month")
        list.add("HE become a billinor and how to get your money back after three days before of the month")
        val linearLayoutManager =
            LinearLayoutManager(baseActivity, LinearLayoutManager.VERTICAL, false)
        binder.rvFAq.layoutManager = linearLayoutManager
        faqadapter = FAQAdapter(baseActivity) {

        }
        faqadapter.addNewList(list)
        binder.rvFAq.adapter = faqadapter
    }

    private fun searchbar() {
        binder.search.edsearchtext.addTextChangedListener {
            var keyword = it.toString()
            Log.e("3324234324edd", keyword.toString())

            var templist = ArrayList<String>()
            list.forEach {
                if (it.lowercase().contains(keyword.lowercase())) {
                    templist.clear()
                    //     list.add(it)
                    templist.add(it)
                    Log.e("addTextChangedListener", templist.size.toString())

                    if (keyword.lowercase().isEmpty()) {
                        faqadapter.addNewList(list)
                    } else {
                        faqadapter.addNewList(templist)
                    }

                    faqadapter.notifyDataSetChanged()
                }
            }


        }

//        binder.search.edsearchtext.addTextChangedListener(object : TextWatcher{
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                Log.e("000000000000",p0.toString())
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//                Log.e("22222222222222",p0.toString())
//            }
//
//        })


    }

    private fun setClick() {
        binder.loginbutton.setOnClickListener {
            baseActivity.openA(FAQQueryActivity::class)

        }
    }

}