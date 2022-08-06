package com.app.qrcodescanner.ui.feedback

 import android.os.Bundle
 import android.os.Handler
 import android.os.Looper
 import android.text.TextUtils
 import android.util.Log
 import android.widget.AdapterView
import com.app.qrcodescanner.R
import com.app.qrcodescanner.adapter.AutoCompleteAuthoriseAdapter
import com.app.qrcodescanner.adapter.SelectionAdapter
import com.app.qrcodescanner.applications.QrApplication
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.*
import com.app.qrcodescanner.listner.AutocompletListner
import com.app.qrcodescanner.model.AuthoriseList
import com.app.qrcodescanner.model.CareListJson
import com.app.qrcodescanner.model.FeebackListJson
import com.app.qrcodescanner.model.FeedbackJson
import com.app.qrcodescanner.reposiory.CommonRepository
import com.app.qrcodescanner.ui.HomeScreenActivity
import com.app.qrcodescanner.ui.Signatures
 import com.app.qrcodescanner.ui.ThankYou
 import com.app.qrcodescanner.utils.Keys
import com.app.qrcodescanner.utils.Utils
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_feed_back.*
import kotlinx.android.synthetic.main.activity_feed_back.clearsign
import kotlinx.android.synthetic.main.activity_feed_back.inchargeauto
import kotlinx.android.synthetic.main.activity_feed_back.ivsignature
import kotlinx.android.synthetic.main.activity_feed_back.llsign
import kotlinx.android.synthetic.main.activity_feed_back.tvsign
import kotlinx.android.synthetic.main.common_toolbar.*
import okhttp3.MultipartBody
import java.io.File
import java.lang.StringBuilder

class FeedBack : KotlinBaseActivity(), AutocompletListner {
    var rbvalue = ""
    var authrisecompanyId = ""
    var setauthrisecompanyId = ""
    val commonRepository = CommonRepository(QrApplication.myApp!!)
    var feedbacklist = ArrayList<FeebackListJson.Data>()
    var authriseList = ArrayList<AuthoriseList.Data>()
    var authrisestringlist = ArrayList<String>()
    var idslist = ArrayList<String>()
    var isauth = true
    var model: FeedbackJson? = null
    var stringBuilder: StringBuilder? = null

    private var autjorisedautoSuggestAdapter: AutoCompleteAuthoriseAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_back)
        settoolbar()
        setclicks()
        feedbacklist()
        getfeedbackdata()
        getauthrisedadapter()
        setdata()

    }
    private  fun setdata()
    {
        tvname.text = HomeScreenActivity.userdata!!.data.user.first_name.capitalizesLetters()+" "+HomeScreenActivity.userdata!!.data.user.last_name.capitalizesLetters()
        tvpostion.text = HomeScreenActivity.userdata!!.data.user.role.capitalizesLetters()

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
            AdapterView.OnItemClickListener { parent, view, position, id ->
                isauth=false
                var list = authriseList.filter { it.name.equals(inchargeauto.text.toString()) }
               if (list.size > 0) {

                    setauthrisecompanyId = list[0].id.toString()
                   Log.e("authriseiddd",setauthrisecompanyId)
                   refreshparms()
               }
            })
        inchargeauto.afterTextChangedDelayed {

            if (!TextUtils.isEmpty(inchargeauto.getText()) && isauth) {

                getauthriseduser(inchargeauto.getText().toString())
            }
            else{
                authclear()
            }
//            else{
//                setauthrisecompanyId=""
//            }
        }
    }
    private  fun  refreshparms()
    {
        Handler(Looper.getMainLooper()).postDelayed({

            isauth=true
        }, 1000)
    }

    private fun getfeedbackdata() {
        commonRepository.getfeedbackdata(this, HomeScreenActivity.token, Keys.BASE_URL + Keys.FEEDBACKDATA+intent.extras!!.getString("punch_in") ) {

            model = it
            if (it.data.work.isNotNull()) {

                tvdate1.text = Utils.formateDateFromstring(
                    Utils.DATETIMEFORMAT,
                    Utils.DATEFORMAT3,
                    it.data.work.date.toString()
                )
            }
            if (it.data.company.isNotNull()) {
                authrisecompanyId = it.data.company.company_id.toString()
                if(it.data.company.company_logo.isNotNull() &&!it.data.company.company_logo.isEmpty())
                {
                    Picasso.get().load(it.data.company.company_logo).into(ivlogo)
                    ivlogo.visible()
                }
            }

            if (it.data.client.isNotNull()) {
                tvclientname.text = it.data.client.client_name
                clientid = it.data.client.id.toString()
                tvaddress.text = it.data.client.address + " " + it.data.client.postcode

            }
        }
    }
    var clientid=""

    private fun settoolbar() {
        tvtitle.text = "Feedback Form"
        ivdot.gone()
        ivback.setOnClickListener {
            onBackPressed()
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
            isauth = true
            autjorisedautoSuggestAdapter?.setData(authriseList);
            autjorisedautoSuggestAdapter?.notifyDataSetChanged();
            if (it.data.size == 0) {

                var add = "(Add " + inchargeauto.text.toString() + " )"
                authriseList.add(AuthoriseList.Data(name = "No name found $add"))
                autjorisedautoSuggestAdapter?.setData(authriseList);
                inchargeauto.setDropDownHeight(150);
                autjorisedautoSuggestAdapter?.notifyDataSetChanged();

            } else {
                if (it.data.size==1)
                {
                    inchargeauto.setDropDownHeight(220);
                }
                else{
                    inchargeauto.setDropDownHeight(500);
                }


                //inchargebutton.gone()
            }


        }
    }

    private fun feedbacklist() {
        commonRepository.feedbacklist(this, HomeScreenActivity.token, Keys.FEEDBACK) {
            feedbacklist.addAll(it.data)
            setadapter()
        }
    }

    private fun setadapter() {
        var adapter = SelectionAdapter(this) {

        }
        adapter.addNewList(feedbacklist)
        rvlist.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        if (signature.isNotNull()) {
            clearsign.visible()
            ivsignature.visible()
            tvsign.gone()
            Picasso.get().load(signature!!).into(ivsignature)

        }
    }

    private fun submitDAta() {
        val fields = java.util.ArrayList<MultipartBody.Part>()
        //Utils.getMultiPart(Keys.staff_name, tvname.text.toString().trim())?.let { fields.add(it) }
        Utils.getMultiPart(Keys.rating, rbvalue.toFloat().toInt().toString())?.let { fields.add(it) }
        Utils.getMultiPart(Keys.comment, etcomment.text.toString().trim())?.let { fields.add(it) }
        Utils.getMultiPart(Keys.client_id, clientid)
            ?.let { fields.add(it) }
       // Utils.getMultiPart(Keys.position, tvpostion.text.toString().trim())?.let { fields.add(it) }
        //Utils.getMultiPart(Keys.address, tvaddress.text.toString())?.let { fields.add(it) }
        Utils.getMultiPart(Keys.postcode, model!!.data.client.postcode)?.let { fields.add(it) }
        Utils.getMultiPart(Keys.authorized_by_id, setauthrisecompanyId)?.let { fields.add(it) }
        Utils.getMultiPart(Keys.feedback, stringBuilder.toString())?.let { fields.add(it) }
        if (signature != null) {
            Utils.getMultiPart("signature", signature!!)?.let { fields.add(it) }
        }
        commonRepository.addfeedback(this, fields) {
            openA(ThankYou::class)
            finish()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        signature=null
    }

    private fun validations(): Boolean {
        commentlay.error = null


        if (rbvalue.isEmpty())
        {
            showtoast("Please select rating")
            return false
        }
        if (inchargeauto.text.toString().isEmpty()) {
            showtoast("Please enter name of incharge")
            return false
        }
        if (setauthrisecompanyId.isEmpty()) {
            showtoast("Please enter name of incharge")
            return false
        }
        if (etcomment.text.toString().trim().isEmpty()) {
            commentlay.error = "Please enter comments"
            return false
        }

        if (signature == null) {
            showtoast("Please sign")
            return false
        }



        return true
    }

    companion object {
        var signature: File? = null
    }

    private fun setclicks() {
//        Poor"=>"1",
//                "Weak"=>"2",
//                "Good"=>"3",
//                "Very Good"=> "4",
//                "Excellent"=> "5"

        submit.setOnClickListener {
            if (validations()) {
                idslist.clear()
                feedbacklist.forEach {
                    if (it.isfeedback) {
                        idslist.add(it.id.toString())
                    }
                }

                if (idslist.size == 0) {
                    showtoast("Please select score")
                } else {
                    stringBuilder = StringBuilder()
                    for (i in idslist.indices) {
                        stringBuilder!!.append(idslist[i])
                        if (i < idslist.size - 1) {
                            stringBuilder!!.append(",");
                        }
                    }
                    submitDAta()
                }


            }
        }
        cancelbutton.setOnClickListener {
            onBackPressed()
        }
        review_rating.setOnRatingChangeListener { ratingBar, rating1, fromUser ->

            when (rating1.toInt()) {
                5 -> {
                    tvratingvalue.text = "(" + rating1.toInt().toString() + ")" + " Excellent"
                }
                4 -> {
                    tvratingvalue.text = "(" + rating1.toInt().toString() + ")" + "Very Good"

                }
                3 -> {
                    tvratingvalue.text = "(" + rating1.toInt().toString() + ")" + " Good"

                }
                2 -> {
                    tvratingvalue.text = "(" + rating1.toInt().toString() + ")" + " Weak"

                }
                1 -> {
                    tvratingvalue.text = "(" + rating1.toInt().toString() + ")" + " Poor"
                }
                0 -> {
                    tvratingvalue.text = ""
                }

            }
            rbvalue = rating1.toString()
        }

        clearsign.setOnClickListener {
            clearsign.gone()
            ivsignature.gone()
            tvsign.visible()
            signature = null
        }
        llsign.setOnClickListener {
            val bund = Bundle()
            bund.putString("from", "1")
            openA(Signatures::class, bund)
        }
    }
    private  fun authclear()
    {
      //  setauthrisecompanyId=""
        authriseList.clear()
        autjorisedautoSuggestAdapter?.setData(authriseList);
        autjorisedautoSuggestAdapter?.notifyDataSetChanged();
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


    override fun autoclick(
        type: String?,
        authdata: AuthoriseList.Data?,
        caredata: CareListJson.Data?
    ) {
        if (type.equals("3")) {
            if (!inchargeauto.text.toString().isEmpty()) {
                addauthrisedUser()
            } else {
                showtoast("Please enter  name")
            }

        } else {
            setauthrisecompanyId = authdata?.id.toString()

         }
        inchargeauto.dismissDropDown()
    }
}