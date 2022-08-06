package com.app.qrcodescanner.ui.feedback

 import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.qrcodescanner.R
import com.app.qrcodescanner.adapter.FeedbackAdapter
 import com.app.qrcodescanner.applications.QrApplication
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.gone
import com.app.qrcodescanner.extension.visible
import com.app.qrcodescanner.model.FeeedbackListJson
import com.app.qrcodescanner.reposiory.CommonRepository
import com.app.qrcodescanner.ui.HomeScreenActivity
import com.app.qrcodescanner.utils.Keys
import kotlinx.android.synthetic.main.activity_feedback_list.*
import kotlinx.android.synthetic.main.activity_feedback_list.rvlist
 import kotlinx.android.synthetic.main.common_toolbar.*

class FeedbackList : KotlinBaseActivity()
{
    private var loading = true
    private var currentPage = 1
    private var totalpage = 0
    var arrayList=ArrayList<FeeedbackListJson.Data.Data>()
    var commonRepository = CommonRepository(QrApplication.myApp!!)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_list)
        settoolbar()
        callapi()
        setscrolllistner()
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
        commonRepository.feedbacklisting2(this,
            HomeScreenActivity.token, Keys.feedbacklisting+"?page="+currentPage.toString(),loading) {
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
    private  fun settoolbar()
    {
        ivback.setOnClickListener {
            onBackPressed()
        }
        tvtitle.text="Feed backs"
        ivdot.gone()
    }
    private  fun setAdapter()
    {
        if (arrayList.size==0)
        {
            tvnodata.visible()
        }
        else{
            tvnodata.gone()
        }
        val adapter=FeedbackAdapter(this){
            val bund=Bundle()
            bund.putString("id",arrayList[it].id.toString())
            bund.putString("punch_in",arrayList[it].id.toString())
            openA(FeedbackDetail::class,bund)
        }
        adapter.addNewList(arrayList)
        rvlist.adapter= adapter
    }}