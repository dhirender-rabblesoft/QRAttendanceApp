package com.app.qrcodescanner.ui
import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.qrcodescanner.R
import com.app.qrcodescanner.adapter.QrCodeListingAdapter
import com.app.qrcodescanner.applications.QrApplication
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.capitalizesLetters
import com.app.qrcodescanner.extension.gone
import com.app.qrcodescanner.extension.isNotNull
import com.app.qrcodescanner.extension.visible
import com.app.qrcodescanner.model.LoginJson
import com.app.qrcodescanner.model.LoginJson2
import com.app.qrcodescanner.model.QrCodeListingModel
import com.app.qrcodescanner.reposiory.CommonRepository
import com.app.qrcodescanner.utils.Keys
import com.app.qrcodescanner.utils.SharedPreferenceManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.activity_genrate_qr_code.*
import kotlinx.android.synthetic.main.common_toolbar.*
import kotlinx.android.synthetic.main.side_menu_bar.view.*
class GenrateQrCode : KotlinBaseActivity()
{
    var qrCodeListing = ArrayList<QrCodeListingModel.Data.Data>()
    var commonRepository = CommonRepository(QrApplication.myApp!!)
    private var loading = true
    private var currentPage = 1
    private var totalpage = 0
    private var isLoadMore = false
    private val recordPerPage = 10
    lateinit var mLayoutManger: LinearLayoutManager
    private var firstVisibleItemPosition: Int = 0
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genrate_qr_code)
        parsedata()
        setToolbar()
        setscrolllistner()
    }
    override fun onResume()
    {
        super.onResume()
        totalpage=0
        currentPage=1
        loading=true
        setQrCodeListingApi()
    }


    private  fun setscrolllistner()
    {
        rvRecentListAdapter.addOnScrollListener(object : RecyclerView.OnScrollListener()
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
                                setQrCodeListingApi()

                            }
                        }
                    }

                }
            }
        })
    }

    private fun setToolbar()
    {
        ivback.gone()
        tvtitle.setText("Welcome Admin")
         ivdot.setImageResource(R.drawable.ic_shutdown)
        ivdot.setOnClickListener {
            token=""
            SharedPreferenceManager(this).saveString(Keys.USERDATA,"")
            SharedPreferenceManager(this).saveString(Keys.USERID,"")
            SharedPreferenceManager(this).saveString(Keys.TOKEN,"")
            SharedPreferenceManager(this).removeValue(Keys.TOKEN)
             openA(LoginActivity::class)
            finishAffinity()
         }
        addlist.setOnClickListener {
             openA(AddArCode::class)
         }
    }
    private fun setQrCodeListingAdapter() {
        val qrCodeListingAdapter = QrCodeListingAdapter(this) {pos,data->
            val jsonObject = JsonObject()
            jsonObject.addProperty(Keys.qr_code_id, qrCodeListing[pos].id.toString())
            jsonObject.addProperty(Keys.status, data)
            commonRepository.change_qrstatus(this, GenrateQrCode.token,jsonObject){

            }
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
        tvusername.text = userdata?.data?.user?.first_name+" "+userdata?.data?.user?.last_name
        tvuserdesignation.text = userdata?.data?.user?.role?.capitalizesLetters()

    }


    private fun setQrCodeListingApi() {
        commonRepository.qrCodeListing(this, token, Keys.QR_CLIENT_LISTING_END_POINT+"?page="+currentPage.toString(),loading) {
            idPBLoading.gone()
            loading=true
            if (totalpage==0)
            {
                qrCodeListing.clear()
                totalpage=it.data.last_page
            }
            qrCodeListing.addAll(it.data.data)
            setQrCodeListingAdapter()
        }
    }


    companion object {
        var userdata: LoginJson? = null
        var token = ""
    }

}