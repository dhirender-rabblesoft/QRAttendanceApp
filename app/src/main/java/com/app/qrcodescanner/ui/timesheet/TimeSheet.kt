package com.app.qrcodescanner.ui.timesheet

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.AutoCompleteTextView
import com.app.qrcodescanner.R
import com.app.qrcodescanner.adapter.AutoCompleteAuthoriseAdapter
import com.app.qrcodescanner.adapter.AutoCompletePlaceAdapter
import com.app.qrcodescanner.adapter.AutoSuggestAdapter
import com.app.qrcodescanner.adapter.Place
import com.app.qrcodescanner.applications.QrApplication
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.*
import com.app.qrcodescanner.listner.AutocompletListner
import com.app.qrcodescanner.model.AuthoriseList
import com.app.qrcodescanner.model.CareListJson
import com.app.qrcodescanner.model.GetTimeSheetJson
import com.app.qrcodescanner.reposiory.CommonRepository
import com.app.qrcodescanner.ui.HomeScreenActivity
import com.app.qrcodescanner.ui.Signatures
import com.app.qrcodescanner.ui.feedback.FeedBack
import com.app.qrcodescanner.utils.Keys
import com.app.qrcodescanner.utils.Utils
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_time_sheet.*
import kotlinx.android.synthetic.main.common_toolbar.*
import okhttp3.MultipartBody
import java.io.File


class TimeSheet : KotlinBaseActivity(), AutocompletListner
{
    private var autoSuggestAdapter: AutoCompletePlaceAdapter? = null
    private var autjorisedautoSuggestAdapter: AutoCompleteAuthoriseAdapter? = null
    var companynamelist = ArrayList<String>()
    var authrisestringlist = ArrayList<String>()
    val commonRepository = CommonRepository(QrApplication.myApp!!)
    var userid = ""
    var companyid = ""
    var selctcompanyid = ""
    var branch_id = ""
    var unit_id = ""
    var authrisecompanyId = ""
    var setauthrisecompanyId = ""
    var careList = ArrayList<CareListJson.Data>()
    var authriseList = ArrayList<AuthoriseList.Data>()
    var timeshetdata: GetTimeSheetJson? = null
    var isauth = true
    var iscare = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_sheet)
        setadapter()
        settoolbar()
        gettimesheetdata()
        getauthrisedadapter()

    }

    companion object {
        var signature: File? = null
    }

    private fun gettimesheetdata() {
        var url = Keys.BASE_URL + Keys.TIMESHEET + HomeScreenActivity.userdata!!.data.user.id.toString()
        commonRepository.getTimeSheetJson(this, HomeScreenActivity.token, url) {

            if (it.code.equals(Keys.RESPONSE_SUCESS)) {
                timeshetdata = it
                tvstaffid.text = it.data.user.ni_number
                tvstaffpos.text = it.data.user.position
                tvstaffemail.text = it.data.user.email
                tvstaffname.text = it.data.user.first_name + " " + it.data.user.last_name

                if (it.data.work.isNotNull()) {
                    var punch = Utils.formateDateFromstring(
                        Utils.DATETIMEFORMAT,
                        Utils.TIMEFORMAT,
                        it.data.work.punch_in
                    )
                    var punchout = Utils.formateDateFromstring(
                        Utils.DATETIMEFORMAT,
                        Utils.TIMEFORMAT,
                        it.data.work.punch_out
                    )
                    tvdate.text = it.data.work.commencing_date
                    tvworkdate.text = it.data.work.date
                    tvtime.text = punch + " " + punchout
                }
                if (it.data.break_time.isNotNull()) {

                    tvofftime.text = it.data.break_time.time + " " + it.data.break_time.format

                }
                if (it.data.client.isNotNull()) {
                    companyid = it.data.client.client_id.toString()
                    tvcare.text = it.data.client.client_name

                }
                if (it.data.company.isNotNull()) {
                    authrisecompanyId = it.data.company.id.toString()

                }
                if (it.data.client.branch.isNotNull()) {
                    branch_id = it.data.client.branch.id.toString()

                }
                if (it.data.client.unit.isNotNull()) {
                    unit_id = it.data.client.unit.id.toString()

                }
            }
        }
    }

    private fun addunit() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("client_id", companyid)
        jsonObject.addProperty("keyword", clientauto.text.toString())
        commonRepository.addunit(this, HomeScreenActivity.token, jsonObject) {
            if (it.data.isNotNull()) {
                selctcompanyid = it.data.id.toString()
            }

        }
    }

    private fun addauthrisedUser() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("company_id", authrisecompanyId)
        jsonObject.addProperty("keyword", inchargeauto.text.toString())
        commonRepository.addusthrisedUser(this, HomeScreenActivity.token, jsonObject) {
            if (it.data.isNotNull()) {
                setauthrisecompanyId = it.data.id.toString()
            }

        }
    }

    private fun validations(): Boolean {
        if (setauthrisecompanyId.isEmpty()) {
            showtoast("Please enter authorised person name")
            return false
        }
        if (signature == null) {
            showtoast("Please sign authorised signature ")
            return false
        }
        return true
    }

    private fun addtime() {
        val fields = java.util.ArrayList<MultipartBody.Part>()
        Utils.getMultiPart(Keys.user_id, HomeScreenActivity.userdata!!.data.user.id.toString())
            ?.let { fields.add(it) }
        Utils.getMultiPart(Keys.company_id, timeshetdata?.data?.company?.id.toString())
            ?.let { fields.add(it) }
        Utils.getMultiPart(Keys.client_id, timeshetdata?.data?.client?.client_id.toString())
            ?.let { fields.add(it) }
        Utils.getMultiPart(Keys.branch_id, timeshetdata!!.data.client.branch.id.toString())
            ?.let { fields.add(it) }
        Utils.getMultiPart(Keys.punch_in, timeshetdata!!.data.work.punch_in.toString())
            ?.let { fields.add(it) }
        Utils.getMultiPart(Keys.punch_out, timeshetdata!!.data.work.punch_out.toString())
            ?.let { fields.add(it) }
        //Utils.getMultiPart(Keys.break_hours, timeshetdata!!.data.break_time.time.toString() )?.let { fields.add(it) }
        Utils.getMultiPart(Keys.unit_id, selctcompanyid)?.let { fields.add(it) }
        Utils.getMultiPart(Keys.note, ettextarea.text.toString())?.let { fields.add(it) }
        Utils.getMultiPart(Keys.authorized_by_id, setauthrisecompanyId)?.let { fields.add(it) }
        if (signature != null) {
            Utils.getMultiPart("signature", signature!!)?.let { fields.add(it) }
        }
        commonRepository.addtimesheet(this, fields) {
            if (it.data.isNotNull()) {
                openA(FeedBack::class)
            }
        }
    }

    private fun getcatenamelist(word: String) {
        p_Bar.visible()
        var url = Keys.BASE_URL + Keys.UNITLIST + companyid + "&keyword=" + word
        commonRepository.getcarenamelist(this, HomeScreenActivity.token, url) {
            careList.clear()
            p_Bar.invisible()
            companynamelist.clear()
            careList.addAll(it.data)
            careList.forEach {
                companynamelist.add(it.unit)
            }


            autoSuggestAdapter?.setData(careList);
            autoSuggestAdapter?.notifyDataSetChanged();
            if (it.data.size > 0) {
                iscare = true
                carebutton.gone()
                clientauto.setDropDownHeight(500);


            } else {
                iscare = false

                var add = "(Add " + clientauto.text.toString() + " )"
                careList.add(CareListJson.Data(unit = "No unit found $add"))
                autoSuggestAdapter?.setData(careList);
                clientauto.setDropDownHeight(150);
                autoSuggestAdapter?.notifyDataSetChanged();
                //carebutton.visible()
            }


        }
    }

    private fun getauthriseduser(word: String) {
        p_Bar.visible()
        var url = Keys.BASE_URL + Keys.AUTHRISEUSER + authrisecompanyId + "&keyword=" + word
//        var url=Keys.BASE_URL+Keys.AUTHRISEUSER+"3"+"&keyword="+word
        commonRepository.getauthroiselist(this, HomeScreenActivity.token, url) {
            authrisestringlist.clear()
            p_Bar.invisible()
            authriseList.clear()
            authriseList.addAll(it.data)
            authriseList.forEach {
                authrisestringlist.add(it.name)
            }
            autjorisedautoSuggestAdapter?.setData(authriseList);
            autjorisedautoSuggestAdapter?.notifyDataSetChanged();
            if (it.data.size == 0) {
                isauth = false
                var add = "(Add " + inchargeauto.text.toString() + " )"
                authriseList.add(AuthoriseList.Data(name = "No name found $add"))
                autjorisedautoSuggestAdapter?.setData(authriseList);
                inchargeauto.setDropDownHeight(150);
                autjorisedautoSuggestAdapter?.notifyDataSetChanged();

            } else {
                isauth = true
                inchargeauto.setDropDownHeight(500);
                //inchargebutton.gone()
            }


        }
    }

    override fun onResume() {
        super.onResume()
        if (signature.isNotNull()) {
            ivsignature.visible()
//            ivsignature.layoutParams.width=350
//            ivsignature.layoutParams.height=350
//
//            ivsignature.adjustViewBounds=true
            tvsign.gone()
            clearsign.visible()
            Log.e("fileeee", signature!!.path)
            Picasso.get().load(signature!!).into(ivsignature)
        }
    }

    private fun settoolbar() {
        ivdot.gone()
        ivback.setOnClickListener {
            onBackPressed()
        }
        tvtitle.text = "Time Sheet"
        carebutton.setOnClickListener {
            if (!clientauto.text.toString().isEmpty()) {
                addunit()
            } else {
                showtoast("Please enter care name")
            }

        }
        inchargebutton.setOnClickListener {
            if (!inchargeauto.text.toString().isEmpty()) {
                addauthrisedUser()
            } else {
                showtoast("Please enter  name")
            }

        }
        llsign.setOnClickListener {
            openA(Signatures::class)
        }
        submit.setOnClickListener {
            if (validations() && timeshetdata != null) {
                addtime()
            }
        }
        clearsign.setOnClickListener {
            ivsignature.gone()
            tvsign.visible()
            clearsign.gone()
            signature = null
        }
    }

    private fun setadapter() {

        //Setting up the adapter for AutoSuggest
        //Setting up the adapter for AutoSuggest
        autoSuggestAdapter = AutoCompletePlaceAdapter(
            this,
            careList, this
        )
        clientauto.setThreshold(2)
        clientauto.setAdapter(autoSuggestAdapter)
        clientauto.setOnItemClickListener(
            OnItemClickListener { parent, view, position, id ->
                Log.e("clientauto", "clientauto")
//                var list = careList.filter { it.unit.equals(clientauto.text.toString()) }
//                if (list.size > 0) {
//                    selctcompanyid = list[0].id.toString()
//                }
            })

        clientauto.afterTextChangedDelayed {
            if (!TextUtils.isEmpty(clientauto.getText()) && iscare) {
                getcatenamelist(clientauto.getText().toString())
            }
        }


    }

    private fun getauthrisedadapter() {
        //Setting up the adapter for AutoSuggest
        //Setting up the adapter for AutoSuggest
        autjorisedautoSuggestAdapter = AutoCompleteAuthoriseAdapter(
            this,
            authriseList, this
        )
        inchargeauto.setThreshold(2)
        inchargeauto.setAdapter(autjorisedautoSuggestAdapter)
        inchargeauto.setOnItemClickListener(
            OnItemClickListener { parent, view, position, id ->
//                var list = authriseList.filter { it.name.equals(inchargeauto.text.toString()) }
//                if (list.size > 0) {
//                    setauthrisecompanyId = list[0].id.toString()
//                }
            })
        inchargeauto.afterTextChangedDelayed {
            if (!TextUtils.isEmpty(inchargeauto.getText()) && isauth) {
                getauthriseduser(inchargeauto.getText().toString())
            }
        }
    }


    override fun autoclick(
        type: String?,
        authdata: AuthoriseList.Data?,
        caredata: CareListJson.Data?
    ) {
        when (type) {
            "2" -> {
                setauthrisecompanyId = authdata?.id.toString()
                inchargeauto.dismissDropDown()
            }
            "3" -> {
                if (!inchargeauto.text.toString().isEmpty()) {
                    addauthrisedUser()
                } else {
                    showtoast("Please enter  name")
                }
                inchargeauto.dismissDropDown()
            }
            "4" -> {
                clientauto.dismissDropDown()
                selctcompanyid=caredata?.id.toString()
            }
            "1" -> {
                if (!clientauto.text.toString().isEmpty()) {
                    addunit()
                } else {
                    showtoast("Please enter care name")
                }
                clientauto.dismissDropDown()
            }
        }
    }

}