package com.app.qrcodescanner.ui
import android.Manifest
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import com.app.qrcodescanner.R
import com.app.qrcodescanner.applications.QrApplication
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.gone
import com.app.qrcodescanner.extension.isEmailValid
import com.app.qrcodescanner.extension.isNotNull
import com.app.qrcodescanner.extension.visible
import com.app.qrcodescanner.model.QrCodeGenerateModel
import com.app.qrcodescanner.reposiory.CommonRepository
import com.app.qrcodescanner.utils.Keys
import com.example.easywaylocation.EasyWayLocation
import com.example.easywaylocation.Listener
import com.google.gson.JsonObject
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.activity_add_ar_code.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.common_toolbar.*
class AddArCode : KotlinBaseActivity(), Listener
{
    val commonRepository = CommonRepository(QrApplication.myApp!!)
    var clientarray = ArrayList<String>()
    var clientlist = ArrayList<String>()
    var compnyClientlist = ArrayList<String>()
    var compnyClientIdlist = ArrayList<String>()
    var unitlist = ArrayList<String>()
    var unitidlist = ArrayList<String>()
    var branchlist = ArrayList<String>()
    var branchidlist = ArrayList<String>()
    var lat = ""
    var lng = ""
    var selectedClientID = ""
    var selectedCompanyClientID = ""
    var clientAddresss = ""
    lateinit var location: EasyWayLocation
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        location = EasyWayLocation(this, false, false, this)
        setContentView(R.layout.activity_add_ar_code)
        locationPermission()
        settoolbar()
        clientListingApi()
        setClick()
    }

    private fun setClick()
    {
        loginbutton.setOnClickListener {
            clientAddresss = etaddress.text.toString().trim()
            if (validation()) {
                qrGenerateClientAPI()
            }
        }
//        commonRepository.qrCodeGenerate(this,GenrateQrCode.token,)
    }

    private fun qrGenerateClientAPI()
    {
        val jsonObject = JsonObject()
         jsonObject.addProperty("client_id", selectedCompanyClientID)
        jsonObject.addProperty("title", clientAddresss)
        jsonObject.addProperty("unit_id", unitid)
        jsonObject.addProperty("branch_id", brnachid)
        jsonObject.addProperty("latitude", lat)
        jsonObject.addProperty("longitude", lng)
        commonRepository.qrCodeGenerate(this, GenrateQrCode.token,jsonObject){
                showtoast("Address added successfully")
                onBackPressed()
        }
    }

    private fun validation(): Boolean
    {
        addresslayout.error = null
        clientLayout.error = null
        companyclientlayout.error = null
       // pincodelayout.error = null

        if (clientAddresss.isEmpty())
        {
            addresslayout.error = getString(R.string.v_addressvalidation)
            return false
        }
        if (ettime.text.toString().trim().equals("Choose"))
        {
            clientLayout.error = getString(R.string.v_clentvalidation)
            return false
        }
        if (autoclient.text.toString().trim().equals("Choose"))
        {
            companyclientlayout.error = getString(R.string.v_companyclentvalidation)
            return false
        }
//        if (etpincode.text.toString().trim().isEmpty()) {
//            pincodelayout.error ="Please enter pincode"
//            return false
//        }
        return true
    }

    private fun setClientAdapter()
    {
         var timeadapter = ArrayAdapter(this, R.layout.dropdown_item, clientarray)
        ettime.setAdapter(timeadapter)
        ettime.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
               // selectedClientID =position.toString()
                selectedClientID =clientlist[position]
                companyclientListingApi(selectedClientID)

            }
    }
    private fun setcompanyClientAdapter()
    {
         var timeadapter = ArrayAdapter(this, R.layout.dropdown_item, compnyClientlist)
        autoclient.setAdapter(timeadapter)
        autoclient.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
               // selectedClientID =position.toString()
                selectedCompanyClientID =compnyClientIdlist[position]
                unitlistning(selectedCompanyClientID)

            }
    }
    var unitid=""
    private fun setunitadapter()
    {
        var timeadapter = ArrayAdapter(this, R.layout.dropdown_item, unitlist)
        autounitt.setAdapter(timeadapter)
        autounitt.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                // selectedClientID =position.toString()
                unitid =unitidlist[position]


            }
    }
    var brnachid=""
    private fun setBranchAdapter()
    {
        var timeadapter = ArrayAdapter(this, R.layout.dropdown_item, branchlist)
        autobranch.setAdapter(timeadapter)
        autobranch.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                // selectedClientID =position.toString()
                brnachid =branchidlist[position]


            }
    }

    private fun settoolbar()
    {
        ivdot.gone()
        tvtitle.text = "Add Address"
        ivback.setOnClickListener {
            onBackPressed()
        }

    }

    private fun clientListingApi() {
        commonRepository.clientListing(this, GenrateQrCode.token, Keys.COMPANLYLISTING) {
            clientarray.clear()
            clientlist.clear()
            it.data.forEach {
                clientarray.add(it.name)
                clientlist.add(it.id.toString())
            }
            setClientAdapter()
            Log.e("checkClientListingdata", it.toString())
        }

    }
    private fun companyclientListingApi(id:String) {
        commonRepository.clientListing(this, GenrateQrCode.token, Keys.CLIENT_LISTING_END_POINT+id) {
           compnyClientlist.clear()
            compnyClientIdlist.clear()
            it.data.forEach {
                compnyClientlist.add(it.name)
                compnyClientIdlist.add(it.id.toString())
            }
            if (compnyClientIdlist.size>0)
            {
                companyclientlayout.visible()
                setcompanyClientAdapter()
            }

        }

    }
    private fun unitlistning(id:String) {
        commonRepository.clientunitListing(this, GenrateQrCode.token, Keys.CLIENT_UNIT+id) {
            if (it.data.isNotNull())
            {
                unitlist.clear()
                unitidlist.clear()
                branchlist.clear()
                branchidlist.clear()
                it.data.units.forEach {
                    unitidlist.add(it.id.toString())
                    unitlist.add(it.unit.toString())
                }
                it.data.branches.forEach {
                    branchidlist.add(it.id.toString())
                    branchlist.add(it.name.toString())
                }

                if (unitidlist.size>0)
                {
                    unitlayout.visible()
                    setunitadapter()
                }
                if (branchidlist.size>0)
                {
                    branchlayout.visible()
                    setBranchAdapter()

                }
            }
        }

    }

    private fun locationPermission()
    {
        val permissonList = ArrayList<String>()
        permissonList.add(Manifest.permission.ACCESS_FINE_LOCATION)
        permissonList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        PermissionX.init(this)
            .permissions(permissonList)
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    getString(R.string.permisionmsgfirst),
                    getString(R.string.ok),
                    getString(R.string.cancel)
                )
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    getString(R.string.manualpermission),
                    getString(R.string.ok),
                    getString(R.string.cancel)
                )
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
//                    openA(Scanner::class)
//                    checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE)
//                    baseActivity.openA(Scanner::class)
//                    ischeckin = true
                    Log.e("permisssion granted", "permission granted")
                }

            }
    }

    override fun locationOn() {

    }

    override fun currentLocation(location: Location?) {
        if (location.isNotNull()) {
            lat = location?.latitude.toString()
            lng = location?.longitude.toString()
            Log.e("latitude_location1542 ", location?.latitude.toString())
            Log.e("logitue location ", location?.longitude.toString())
//            calculateDistance()
        } else {
            Log.e("latitude_location1542 ", "dsfsdfsddsf")
        }

    }

    override fun locationCancelled() {

    }

    override fun onResume() {
        super.onResume()
        location.startLocation()
    }


}