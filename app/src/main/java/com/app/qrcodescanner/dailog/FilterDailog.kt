package com.app.qrcodescanner.dailog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.DailogBaseFragment
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.FragmentFilterDailogBinding
import com.app.qrcodescanner.extension.gone
import com.app.qrcodescanner.extension.visible
import com.app.qrcodescanner.model.FilterModel
import com.app.qrcodescanner.utils.Utils
import kotlinx.android.synthetic.main.activity_main.view.*


class FilterDailog(
    var icon: String,
    var msg: String,
    var buttontext: String,
    var baseActivity: KotlinBaseActivity,
    var itemClick: (String,String,String) -> Unit,

    ) : DailogBaseFragment() {

    lateinit var binding: FragmentFilterDailogBinding
    var sortbylist = arrayOf("Date ASC", "Date DSE")
    var filterlist: ArrayList<FilterModel>? = null
    var isDateShow = true
    var spinner_value = ""
    var radioType=""

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_filter_dailog, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setClicks()
        setspinner()
    }
    private fun setspinner()
    {
        val adapter = ArrayAdapter(baseActivity, android.R.layout.simple_spinner_dropdown_item, sortbylist)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //Setting the ArrayAdapter data on the Spinner
        //Setting the ArrayAdapter data on the Spinner
        binding.sortbySpinner.setAdapter(adapter)
    }
    private  fun clearfilter()
    {
        binding.startdate.setText("")
        binding.enddate.setText("")
    }

    private fun setClicks()
    {
        binding.sortbySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                pos: Int,
                long: Long
            ) {
                spinner_value = parent?.getItemAtPosition(pos).toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, checkId ->

            when (checkId) {
                R.id.last7days -> {
                    radioType="2"
                    binding.datecontainer.gone()
                    clearfilter()

                }
                R.id.last30days -> {
                    radioType="1"
                    binding.datecontainer.gone()
                    clearfilter()

                }
                R.id.custom -> {
                    radioType="3"
                    binding.datecontainer.visible()
                }
                else -> return@setOnCheckedChangeListener
            }


        }

        binding.startdate.setOnClickListener {
            Utils.shoedatepicker(baseActivity, binding.startdate, onConfirmed = {

            })
        }
        binding.enddate.setOnClickListener {
            if (binding.startdate.text.toString().trim().isEmpty()) {
                baseActivity.showtoast("Please Select Start Date First")
                 return@setOnClickListener
            }
            Utils.shoedatepicker(baseActivity, binding.enddate, onConfirmed = {

            })

        }

        binding.btfilter.setOnClickListener {
          if ( binding.radioGroup.checkedRadioButtonId==-1)
          {
              baseActivity.showtoast("Please select  option")
          }
            else{
                if (radioType.equals("3"))
                {
                    if (binding.startdate.text.toString().trim().isEmpty())
                    {
                        baseActivity.showtoast("Please select start date")
                    }
                    else if (binding.enddate.text.toString().trim().isEmpty())
                    {
                        baseActivity.showtoast("Please select end date")
                    }
                    else{
                        callapi()
                    }
                }
              else{
                    callapi()
                }

          }

        }

        binding.btcancel.setOnClickListener {
            dismiss()
        }


    }
    private  fun callapi()
    {
        itemClick(radioType,binding.startdate.text.toString(),binding.enddate.text.toString())
        dismiss()
    }

}