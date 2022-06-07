package com.app.qrcodescanner.base

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.app.qrcodescanner.utils.Utils

/**
 * The type Dialog base fragment.
 */
open class DailogBaseFragment : DialogFragment() {
    /**
     * The M context.
     */
    var mContext: Context? = null

    /**
     * The Bundle.
     */
    var bundle: Bundle? = null

    /**
     * The Composite disposable.
     */

    // private ProgressBarHandler progressBar;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, 0)
        mContext = activity
        bundle = arguments
        if (bundle == null) bundle = Bundle()

        //  progressBar = new ProgressBarHandler(getActivity());
    }

    override fun onResume() {
        super.onResume()
        Utils.setDialogAttributes(
            dialog!!,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )
    }

    fun showtoast(msg: String) {
        val myToast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT)
        myToast.setGravity(Gravity.CENTER, 0, 0)
        myToast.show()
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    /**
     * Server error.
     * Getting error response from server when any api hit.
     *
     * @param throwable the throwable
     */
    fun serverError(throwable: Throwable?) {
        //  progressBar.hide();
        //    ((BaseActivity) mContext).serverError(throwable);
    }
}