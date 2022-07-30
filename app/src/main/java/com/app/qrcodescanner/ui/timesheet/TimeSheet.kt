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
import com.app.qrcodescanner.applications.QrApplication
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.gone
import com.app.qrcodescanner.extension.isNotNull
import com.app.qrcodescanner.extension.visible
import com.app.qrcodescanner.model.AuthoriseList
import com.app.qrcodescanner.model.CareListJson
import com.app.qrcodescanner.model.GetTimeSheetJson
import com.app.qrcodescanner.reposiory.CommonRepository
import com.app.qrcodescanner.ui.GenrateQrCode
import com.app.qrcodescanner.ui.HomeScreenActivity
import com.app.qrcodescanner.ui.Signatures
import com.app.qrcodescanner.ui.feedback.FeedBack
import com.app.qrcodescanner.utils.Keys
import com.app.qrcodescanner.utils.SharedPreferenceManager
import com.app.qrcodescanner.utils.Utils
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_time_sheet.*
import kotlinx.android.synthetic.main.common_toolbar.*
import okhttp3.MultipartBody
import java.io.File
class TimeSheet : KotlinBaseActivity()
{

    private val TRIGGER_AUTO_COMPLETE = 100
    private val AUTO_COMPLETE_DELAY: Long = 300
    private var handler: Handler? = null
    private var inchargehandler: Handler? = null
    private var autoSuggestAdapter: AutoSuggestAdapter? = null
    private var autjorisedautoSuggestAdapter: AutoSuggestAdapter? = null
    var companynamelist=ArrayList<String>()
    var authrisestringlist=ArrayList<String>()
    val commonRepository= CommonRepository(QrApplication.myApp!!)
    var userid=""
    var companyid=""
    var selctcompanyid=""
    var branch_id=""
    var unit_id=""
    var authrisecompanyId=""
    var setauthrisecompanyId=""
    var careList=ArrayList<CareListJson.Data>()
    var authriseList=ArrayList<AuthoriseList.Data>()
    var timeshetdata:GetTimeSheetJson?=null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_sheet)
        setadapter()
        settoolbar()
        gettimesheetdata()
        getauthrisedadapter()
    }
    companion object{
        var signature:File?=null
    }
    private  fun gettimesheetdata()
    {
        var url=Keys.BASE_URL+Keys.TIMESHEET+"53"
        commonRepository.getTimeSheetJson(this,HomeScreenActivity.token,url){

        if (it.code.equals(Keys.RESPONSE_SUCESS))
        {
            timeshetdata=it
            tvstaffid.text=it.data.user.ni_number
            tvstaffpos.text=it.data.user.position
            tvstaffemail.text=it.data.user.email
            tvstaffname.text=it.data.user.first_name+" "+it.data.user.last_name

            if (it.data.work.isNotNull())
            {
                var punch =Utils.formateDateFromstring(Utils.DATETIMEFORMAT, Utils.TIMEFORMAT,it.data.work.punch_in)
                var punchout =Utils.formateDateFromstring(Utils.DATETIMEFORMAT, Utils.TIMEFORMAT,it.data.work.punch_out)
                tvdate.text=it.data.work.commencing_date
                tvworkdate.text=it.data.work.date
                tvtime.text=punch+" "+punchout
            }
            if (it.data.break_time.isNotNull())
            {

                tvofftime.text=it.data.break_time.time +" "+it.data.break_time.format

            }
            if (it.data.client.isNotNull())
            {
                companyid=it.data.client.client_id.toString()
                tvcare.text=it.data.client.client_name

            }
            if (it.data.company.isNotNull())
            {
                authrisecompanyId=it.data.company.id.toString()

            }
            if (it.data.client.branch.isNotNull())
            {
                branch_id=it.data.client.branch.id.toString()

            }
            if (it.data.client.unit.isNotNull())
            {
                unit_id=it.data.client.unit.id.toString()

            }
        }
        }
    }
    private  fun addunit()
    {
        val jsonObject = JsonObject()
        jsonObject.addProperty("client_id", companyid)
        jsonObject.addProperty("keyword", clientauto.text.toString())
        commonRepository.addunit(this, HomeScreenActivity.token,jsonObject){
            if (it.data.isNotNull())
            {
                selctcompanyid=it.data.id.toString()
            }

        }
    }
    private  fun addauthrisedUser()
    {
        val jsonObject = JsonObject()
        jsonObject.addProperty("company_id", authrisecompanyId)
        jsonObject.addProperty("keyword", inchargeauto.text.toString())
        commonRepository.addusthrisedUser(this, HomeScreenActivity.token,jsonObject){
            if (it.data.isNotNull())
            {
                setauthrisecompanyId=it.data.id.toString()
            }

        }
    }
    private  fun validations():Boolean
    {
        if (setauthrisecompanyId.isEmpty())
        {
            showtoast("Please enter authorised person name")
            return false
        }
        if (signature==null)
        {
            showtoast("Please sign authorised signature ")
            return false
        }
        return true
    }
    private  fun addtime()
    {
        val fields = java.util.ArrayList<MultipartBody.Part>()
        Utils.getMultiPart(Keys.user_id, HomeScreenActivity.userdata!!.data.user.id.toString())?.let { fields.add(it) }
        Utils.getMultiPart(Keys.company_id, timeshetdata?.data?.company?.id.toString() )?.let { fields.add(it) }
        Utils.getMultiPart(Keys.client_id, timeshetdata?.data?.client?.client_id.toString() )?.let { fields.add(it) }
        Utils.getMultiPart(Keys.branch_id, timeshetdata!!.data.client.branch.id.toString() )?.let { fields.add(it) }
        Utils.getMultiPart(Keys.unit_id, selctcompanyid )?.let { fields.add(it) }
        Utils.getMultiPart(Keys.note, ettextarea.text.toString() )?.let { fields.add(it) }
        Utils.getMultiPart(Keys.authorized_by_id, setauthrisecompanyId )?.let { fields.add(it) }
        if (signature != null) {
            Utils.getMultiPart("signature", signature!!)?.let { fields.add(it) }
        }
        commonRepository.addtimesheet(this,fields){
            if (it.data.isNotNull())
            {
                openA(FeedBack::class)
            }
        }
    }

    private  fun getcatenamelist(word:String)
    {
        var url=Keys.BASE_URL+Keys.UNITLIST+companyid+"&keyword="+word
         commonRepository.getcarenamelist(this,HomeScreenActivity.token,url){
             careList.clear()

             companynamelist.clear()
             careList.addAll(it.data)
             careList.forEach {
                 companynamelist.add(it.unit)
             }
             autoSuggestAdapter?.setData(companynamelist);
             autoSuggestAdapter?.notifyDataSetChanged();
            if (it.data.size>0)
            {
                carebutton.gone()
            }
            else{
                carebutton.visible()
            }



        }
    }
    private  fun getauthriseduser(word:String)
    {
        var url=Keys.BASE_URL+Keys.AUTHRISEUSER+authrisecompanyId+"&keyword="+word
//        var url=Keys.BASE_URL+Keys.AUTHRISEUSER+"3"+"&keyword="+word
         commonRepository.getauthroiselist(this,HomeScreenActivity.token,url){
             authrisestringlist.clear()

             authriseList.clear()
             authriseList.addAll(it.data)
             authriseList.forEach {
                 authrisestringlist.add(it.name)
             }
             autjorisedautoSuggestAdapter?.setData(authrisestringlist);
             autjorisedautoSuggestAdapter?.notifyDataSetChanged();
            if (it.data.size==0)
            {
                inchargebutton.visible()
            }
            else{
                inchargebutton.gone()
            }



        }
    }

    override fun onResume() {
        super.onResume()
        if (signature.isNotNull())
        {
            ivsignature.visible()
            tvsign.gone()
            clearsign.visible()
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
        carebutton.setOnClickListener {
            if (!clientauto.text.toString().isEmpty())
            {
                addunit()
            }
            else{
                showtoast("Please enter care name")
            }

        }
        inchargebutton.setOnClickListener {
            if (!inchargeauto.text.toString().isEmpty())
            {
                addauthrisedUser()
            }
            else{
                showtoast("Please enter  name")
            }

        }
        llsign.setOnClickListener {
            openA(Signatures::class)
        }
        submit.setOnClickListener {
             if (validations() &&timeshetdata!=null)
             {
                 addtime()
             }
        }
        clearsign.setOnClickListener {
            ivsignature.gone()
            tvsign.visible()
            clearsign.gone()
            signature=null
        }
    }
    private  fun setadapter()
    {

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
                var list=careList.filter { it.unit.equals(clientauto.text.toString()) }
                if (list.size>0)
                {
                    selctcompanyid=list[0].id.toString()
                 }
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


        handler = Handler { msg ->
            if (msg.what === TRIGGER_AUTO_COMPLETE) {
                if (!TextUtils.isEmpty(clientauto.getText())) {
                    getcatenamelist(clientauto.getText().toString())
                }
            }
            false
        }
    }
    private  fun getauthrisedadapter()
    {

        //Setting up the adapter for AutoSuggest
        //Setting up the adapter for AutoSuggest
        autjorisedautoSuggestAdapter = AutoSuggestAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line
        )
        inchargeauto.setThreshold(2)
        inchargeauto.setAdapter(autjorisedautoSuggestAdapter)
        inchargeauto.setOnItemClickListener(
            OnItemClickListener { parent, view, position, id ->
                var list=authriseList.filter { it.name.equals(inchargeauto.text.toString()) }
                if (list.size>0)
                {
                    setauthrisecompanyId=list[0].id.toString()
                 }
            })
        inchargeauto.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
                inchargehandler!!.removeMessages(TRIGGER_AUTO_COMPLETE)
                inchargehandler?.sendEmptyMessageDelayed(
                    TRIGGER_AUTO_COMPLETE,
                    AUTO_COMPLETE_DELAY
                )
            }

            override fun afterTextChanged(s: Editable) {}
        })


        inchargehandler = Handler { msg ->
            if (msg.what === TRIGGER_AUTO_COMPLETE) {
                if (!TextUtils.isEmpty(inchargeauto.getText())) {
                    getauthriseduser(inchargeauto.getText().toString())
                }
            }
            false
        }
    }
}