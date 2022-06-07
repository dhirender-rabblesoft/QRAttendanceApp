package com.app.qrcodescanner.dailog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.DailogBaseFragment
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.FragmentComfirmDailogBinding
import com.app.qrcodescanner.ui.HomeScreenActivity

class ComfirmDailogue(
    var icon: String,
    var msg: String,
    var buttontext: String,
    var baseActivity: KotlinBaseActivity,
    var itemClick: (Int) -> Unit
) : DailogBaseFragment() {

    lateinit var binding: FragmentComfirmDailogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_comfirm_dailog, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setData()
        setClick()
    }

    private fun setData() {
        binding.tvDialogue.setText(msg)

    }

    private fun setClick() {
        binding.btcomfirm.setOnClickListener {
            baseActivity.openA(HomeScreenActivity::class)
            dismiss()
        }
        binding.btcancel.setOnClickListener {
            baseActivity.openA(HomeScreenActivity::class)
            dismiss()
        }
    }


}