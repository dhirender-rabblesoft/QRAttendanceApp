package com.app.qrcodescanner.ui.feedback
import android.os.Bundle
import com.app.qrcodescanner.R
import com.app.qrcodescanner.adapter.ViewSelectionAdapter
import com.app.qrcodescanner.applications.QrApplication
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.capitalizesLetters
import com.app.qrcodescanner.extension.gone
import com.app.qrcodescanner.extension.isNotNull
import com.app.qrcodescanner.model.FeedbackDetailJson
import com.app.qrcodescanner.reposiory.CommonRepository
import com.app.qrcodescanner.ui.HomeScreenActivity
import com.app.qrcodescanner.utils.Keys
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_feedback_detail.*
import kotlinx.android.synthetic.main.activity_feedback_detail.tvclientname
import kotlinx.android.synthetic.main.common_toolbar.*
class FeedbackDetail : KotlinBaseActivity()
{
    var commonRepository = CommonRepository(QrApplication.myApp!!)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_detail)
        settoolbar()
        feedbackdata()
    }
    private  fun settoolbar()
    {
        tvname.text=HomeScreenActivity.userdata!!.data.user.first_name.capitalizesLetters()+" "+HomeScreenActivity.userdata!!.data.user.last_name.capitalizesLetters()
        tvpostion.text=HomeScreenActivity.userdata!!.data.user.role.capitalizesLetters()
        ivback.setOnClickListener {
            onBackPressed()
        }
        tvtitle.text="Feedback"
        ivdot.gone()

    }

    fun setscoreadapter(list: ArrayList<FeedbackDetailJson.Data.Feedback.FeedbackOption>)
    {
        val adapter= ViewSelectionAdapter(this){

        }
        adapter.addNewList(list)
        rvlist1.adapter=adapter
    }

    private  fun feedbackdata()
    {
        var url= Keys.getfeedback+"?id="+intent.extras!!.getString("id")
        commonRepository.feedbackdetail(this,HomeScreenActivity.token,url)
        {
            if (it.data.feedback.feedback.isNotNull())
            {
                tvclientname.text=it.data.feedback.feedback.client.name
                tvcomments.text=it.data.feedback.feedback.comment
                var address2=""
                setscoreadapter(it.data.feedback.feedback_options)
                if (it.data.feedback.feedback.client.address_2.isNotNull())
                {
                    address2  = it.data.feedback.feedback.client.address_2.toString()
                }
                if (it.data.feedback.feedback.signature.isNotNull())
                {
                  Picasso.get().load(it.data.feedback.feedback.signature).into(ivsign)
                }

                if (it.data.feedback.feedback.authorized_by.isNotNull())
                {
                    tvinchargename.text=it.data.feedback.feedback.authorized_by.name

                }
                when(it.data.feedback.feedback.rating)
                {
                    "5"->{
                        tvratingvalue.text="(5)"+" Excellent"
                        review_rating.rating= 5.0F
                    }
                    "4"->{
                        review_rating.rating= 4.0F
                        tvratingvalue.text="(4)"+" Very Good"

                    }
                    "3"->{
                        review_rating.rating= 3.0F
                        tvratingvalue.text="(3)"+" Good"

                    }
                    "2"->{
                        review_rating.rating= 2.0F
                        tvratingvalue.text="(2)"+" Weak"

                    }
                    "1"->{
                        review_rating.rating= 1.0F
                        tvratingvalue.text="(1)"+" Poor"

                    }
                }

                tvaddress.text=it.data.feedback.feedback.client.address+" $address2 "+it.data.feedback.feedback.client.post_code
            }
        }
    }

}