package com.app.qrcodescanner.ui

 import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.utils.signature.SilkySignaturePad
import androidx.appcompat.widget.AppCompatButton
import android.os.Bundle
import com.app.qrcodescanner.R
import com.app.qrcodescanner.utils.signature.SilkySignaturePad.OnSignedListener
import android.widget.Toast
import android.graphics.Bitmap
import android.os.Environment
import kotlin.Throws
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.View
 import com.app.qrcodescanner.extension.gone
 import com.app.qrcodescanner.ui.feedback.FeedBack
 import com.app.qrcodescanner.ui.timesheet.TimeSheet
 import kotlinx.android.synthetic.main.common_toolbar.*
 import java.io.*

class Signatures : KotlinBaseActivity() {
    private var mSilkySignaturePad: SilkySignaturePad? = null
    private var mClearButton: AppCompatButton? = null
    private var mSaveButton: AppCompatButton? = null
    private var mCompressButton: AppCompatButton? = null
    var iscare=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signatures)
        mSilkySignaturePad = findViewById(R.id.signature_pad)
        settoolbar()
        mSilkySignaturePad!!.setOnSignedListener(object : OnSignedListener {
            override fun onStartSigning() {
          //      Toast.makeText(this@Signatures, "OnStartSigning", Toast.LENGTH_SHORT).show()
            }

            override fun onSigned() {
                mSaveButton!!.isEnabled = true
                mClearButton!!.isEnabled = true
                mCompressButton!!.isEnabled = true
            }

            override fun onClear() {
                mSaveButton!!.isEnabled = false
                mClearButton!!.isEnabled = false
                mCompressButton!!.isEnabled = false
            }
        })
        mClearButton = findViewById(R.id.clear_button)
        mSaveButton = findViewById(R.id.save_button)
        mClearButton!!.setOnClickListener(View.OnClickListener { mSilkySignaturePad?.clear() })
        mSaveButton!!.setOnClickListener(View.OnClickListener {
                            Toast.makeText(
                    this@Signatures,
                    "Please wait while generate signature ",
                    Toast.LENGTH_SHORT
                ).show()
            val signatureBitmap = mSilkySignaturePad?.getTransparentSignatureBitmap(true)
            if (addJpgSignatureToGallery(signatureBitmap!!)) {
//                Toast.makeText(
//                    this@Signatures,
//                    "Signature saved into the Gallery",
//                    Toast.LENGTH_SHORT
//                ).show()
            } else {
//                Toast.makeText(this@Signatures, "Unable to store the signature", Toast.LENGTH_SHORT)
//                    .show()
            }
//            if (addSvgSignatureToGallery(mSilkySignaturePad!!.getSignatureSvg())) {
////                Toast.makeText(
////                    this@Signatures,
////                    "SVG Signature saved into the Gallery",
////                    Toast.LENGTH_SHORT
////                ).show()
//            } else {
////                Toast.makeText(
////                    this@Signatures,
////                    "Unable to store the SVG signature",
////                    Toast.LENGTH_SHORT
////                ).show()
//            }
        })
        mCompressButton = findViewById(R.id.compress_button)
        mCompressButton?.setOnClickListener(View.OnClickListener {
            val signatureBitmap = mSilkySignaturePad?.getCompressedSignatureBitmap(50)
            if (addJpgSignatureToGallery(signatureBitmap!!)) {
//                Toast.makeText(
//                    this@Signatures,
//                    "50% compressed signature saved into the Gallery",
//                    Toast.LENGTH_SHORT
//                ).show()
            } else {
//                Toast.makeText(this@Signatures, "Unable to store the signature", Toast.LENGTH_SHORT)
//                    .show()
            }
        })
    }
    private  fun settoolbar()
    {
        ivback.setOnClickListener {
            onBackPressed()
        }
        tvtitle.text="Signature"
        ivdot.gone()
    }

    fun getAlbumStorageDir(albumName: String?): File {
        // Get the directory for the user's public pictures directory.
        val file = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
            ), albumName
        )
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created")
        }
        return file
    }

    @Throws(IOException::class)
    fun saveBitmapToJPG(bitmap: Bitmap, photo: File?) {
        val newBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(newBitmap)
        canvas.drawColor(Color.WHITE)
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        val stream: OutputStream = FileOutputStream(photo)
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        stream.close()
    }

    @SuppressLint("DefaultLocale")
    fun addJpgSignatureToGallery(signature: Bitmap): Boolean {
        var result = false
        try {
            val photo = File(
                getAlbumStorageDir("SignaturePad"),
                String.format("Signature_%d.jpg", System.currentTimeMillis())
            )
            if (intent.extras!=null && intent?.extras!!.getString("from").equals("1"))
            {
                FeedBack.signature=photo

            }
            else{
                TimeSheet.signature=photo

            }
            Log.e("photoooo1", photo.path)
            saveBitmapToJPG(signature, photo)
           // scanMediaFile(photo)
            result = true
            onBackPressed()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return result
    }

    private fun scanMediaFile(photo: File) {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val contentUri = Uri.fromFile(photo)
        mediaScanIntent.data = contentUri
        this@Signatures.sendBroadcast(mediaScanIntent)
    }

    @SuppressLint("DefaultLocale")
    fun addSvgSignatureToGallery(signatureSvg: String?): Boolean {
        var result = false
        try {
            val svgFile = File(
                getAlbumStorageDir("SignaturePad"),
                String.format("Signature_%d.svg", System.currentTimeMillis())
            )
            val stream: OutputStream = FileOutputStream(svgFile)
            val writer = OutputStreamWriter(stream)
            writer.write(signatureSvg)
            writer.close()
            stream.flush()
            stream.close()
            scanMediaFile(svgFile)
            result = true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return result
    }
}