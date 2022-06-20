package com.app.qrcodescanner.fragement

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.qrcodescanner.R
import com.app.qrcodescanner.adapter.InvoiceTaskListingAdapter
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.base.KotlinBaseFragment
import com.app.qrcodescanner.databinding.FragmentInvoiceDetailFragementBinding
import com.app.qrcodescanner.extension.isNotNull
import com.app.qrcodescanner.model.InvoiceListJson
import com.app.qrcodescanner.ui.HomeScreenActivity
import com.app.qrcodescanner.utils.Utils
import com.app.qrcodescanner.viewmodel.InvoiceDetailFragmentViewModel
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.fragment_invoice_detail_fragement.*

class InvoiceDetailFragement(val baseActivity: KotlinBaseActivity,val detail:InvoiceListJson.Data) : KotlinBaseFragment() {
    lateinit var binding: FragmentInvoiceDetailFragementBinding
    lateinit var viewmodel: InvoiceDetailFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_invoice_detail_fragement,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel = ViewModelProvider(this).get(InvoiceDetailFragmentViewModel::class.java)
        viewmodel.setBinder(binding,baseActivity)
        setdata()

    }
    private  fun setdata()
    {
        binding.loginbutton.setOnClickListener {
            val permissonList = ArrayList<String>()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                permissonList.add(Manifest.permission.READ_EXTERNAL_STORAGE)

            } else {
                permissonList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                permissonList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }

            PermissionX.init(baseActivity)
                .permissions(permissonList)
                .onExplainRequestReason { scope, deniedList ->
                    scope.showRequestReasonDialog(
                        deniedList,
                        baseActivity.getString(R.string.permisionmsgfirst),
                        baseActivity.getString(R.string.ok),
                        baseActivity.getString(R.string.cancel)
                    )
                }
                .onForwardToSettings { scope, deniedList ->
                    scope.showForwardToSettingsDialog(
                        deniedList,
                        baseActivity.getString(R.string.manualpermission),
                        baseActivity.getString(R.string.ok),
                        baseActivity.getString(R.string.cancel)
                    )
                }
                .request { allGranted, grantedList, deniedList ->
                    if (allGranted) {
                        if (detail.invoice.isNotNull() && !detail.invoice.isEmpty())
                        {
                            downloadImage(detail.invoice)
                        }
                        else{
                            baseActivity.showtoast("invalid Link")
                        }
                    } else {

                        baseActivity.showtoast(baseActivity.getString(R.string.nopermissiongrant))
                    }
                }


        }

        if (!detail.status.equals(1))
        {
            binding.tvinvoicestatus.text="Pending"
            binding.tvinvoicestatus.background=baseActivity.getDrawable(R.drawable.red_rectangle_round_background)
        }
        binding.tvinvoiceId.text=detail.invoice_number
        binding.tvinvocedate.text= detail.created_at
        binding.tvinvoiceamount.text="$"+detail.price.toString()
        binding.tvtotalprice.text="$"+detail.price.toString()
        binding.tvusername.text=HomeScreenActivity.userdata?.data?.user?.first_name+" "+HomeScreenActivity.userdata?.data?.user?.last_name
        binding.tvempemail.text=HomeScreenActivity.userdata?.data?.user?.email
        if (detail.note.isNotNull())
        {
            var  list=ArrayList<String>()
            list.add(detail.note)
            setInvoiceTaskAdapter(list)
        }
    }
    private fun setInvoiceTaskAdapter(list:ArrayList<String>) {
        val linearLayoutManager =
            LinearLayoutManager(baseActivity, LinearLayoutManager.VERTICAL, false)
        binding.rvinvoicelist.layoutManager = linearLayoutManager
        val invoiceTaskListingAdapter = InvoiceTaskListingAdapter(baseActivity) {

        }
        invoiceTaskListingAdapter.addNewList(list)
        binding.rvinvoicelist.adapter = invoiceTaskListingAdapter


    }

}