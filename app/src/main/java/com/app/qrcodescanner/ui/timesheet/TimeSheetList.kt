package com.app.qrcodescanner.ui.timesheet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.qrcodescanner.R
import com.app.qrcodescanner.adapter.TimesheetListingAdapter
import com.app.qrcodescanner.applications.QrApplication
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.gone
import com.app.qrcodescanner.extension.visible
import com.app.qrcodescanner.model.TimeSheetListJson
import com.app.qrcodescanner.model.TimelistJson
import com.app.qrcodescanner.reposiory.CommonRepository
import com.app.qrcodescanner.ui.GenrateQrCode
import com.app.qrcodescanner.ui.HomeScreenActivity
import com.app.qrcodescanner.utils.Keys
import kotlinx.android.synthetic.main.activity_genrate_qr_code.*
import kotlinx.android.synthetic.main.activity_time_sheet_list.*
import kotlinx.android.synthetic.main.activity_time_sheet_list.idPBLoading
import kotlinx.android.synthetic.main.common_toolbar.*

class TimeSheetList : KotlinBaseActivity()
{
    private var loading = true
    private var currentPage = 1
    private var totalpage = 0
    var commonRepository = CommonRepository(QrApplication.myApp!!)
    var arrayList=ArrayList<TimeSheetListJson.Data.Data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_sheet_list)
        settoolbar()
        setAdapter()
        settoolbar()

        setscrolllistner()
    }

    override fun onResume() {
        super.onResume()
        arrayList.clear()
        callapi()
    }
    private  fun settoolbar()
    {
        ivback.setOnClickListener {
            onBackPressed()
        }
        tvtitle.text="Time Sheet"
        ivdot.gone()
    }
    private  fun setscrolllistner()
    {
        rvlist.addOnScrollListener(object : RecyclerView.OnScrollListener()
        {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                    val total = layoutManager!!.itemCount
                    val currentLastItem: Int = layoutManager.findLastVisibleItemPosition()
                    if (currentLastItem == total - 1) {
                        if (loading)
                        {
                            loading=false
                            if (currentPage<totalpage)
                            {
                                idPBLoading.visible()
                                currentPage++
                                callapi()


                            }
                        }
                    }

                }
            }
        })
    }
    private  fun callapi()
    {
                            commonRepository.timelisting(this,
            HomeScreenActivity.token, Keys.timesheetlisting+"?page="+currentPage.toString(),loading) {
            idPBLoading.gone()
            loading=true
            if (totalpage==0)
            {

                totalpage=it.data.last_page
            }
            arrayList.addAll(it.data.data)
            setAdapter()

        }
    }

    private  fun setAdapter()
    {
        if (arrayList.size==0)
        {
            tvnodata.visible()
        }
        else{
            tvnodata.gone()
            var  adapter=TimesheetListingAdapter(this){

            }
            adapter.addNewList(arrayList)
            rvlist.adapter=adapter

        }     }
}