package com.example.smart_winery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView

import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.smart_winery.databinding.MainPageBinding
import com.example.smart_winery.databinding.ReserveBinding

import com.example.smart_winery.ScanPage.Companion.startScanner
import com.google.mlkit.vision.barcode.common.Barcode

@GlideModule
class MyGlide : AppGlideModule()


class MainPage : AppCompatActivity() {

    private lateinit var binding: MainPageBinding
    private val cameraPermission = android.Manifest.permission.CAMERA

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            startScanner()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val mainPageBinding = MainPageBinding.inflate(layoutInflater)
        binding = MainPageBinding.inflate(layoutInflater)
        val reserveBinding = ReserveBinding.inflate(layoutInflater)
        val reserveView = reserveBinding.root

        val url = "http://13.48.52.200:3000/winecellar/winename?id=64ae2b9048a3d71c485e2476"

        val queue : RequestQueue = Volley.newRequestQueue(applicationContext)
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->

        }, { error ->
            Log.e("TAGa", "RESPONSE IS $error")
            // in this case we are simply displaying a toast message.
            Toast.makeText(this@MainPage, "Fail to get response", Toast.LENGTH_SHORT)
                .show()
        })

        queue.add(request)

        val firstfloor = arrayListOf<ImageView>(
            mainPageBinding.btn11,
            mainPageBinding.btn12,
            mainPageBinding.btn13,
            mainPageBinding.btn14,
            mainPageBinding.btn15
        )
        val secondfloor = arrayListOf<ImageView>(
            mainPageBinding.btn21,
            mainPageBinding.btn22,
            mainPageBinding.btn23,
            mainPageBinding.btn24,
            mainPageBinding.btn25
        )
        val thirdfloor = arrayListOf<ImageView>(
            mainPageBinding.btn31,
            mainPageBinding.btn32,
            mainPageBinding.btn33,
            mainPageBinding.btn34,
            mainPageBinding.btn35
        )
        for (i in firstfloor){
            GlideApp.with(this)
                .load("")
                .into(i)
            i.clipToOutline = true
        }
        for (i in secondfloor){
            GlideApp.with(this)
                .load("")
                .into(i)
            i.clipToOutline = true
        }
        for (i in thirdfloor){
            GlideApp.with(this)
                .load("")
                .into(i)
            i.clipToOutline = true
        }

        setContentView(mainPageBinding.root)
        mainPageBinding.addWine.setOnClickListener() {
            requestCameraAndStartScanner()
//            val intent = Intent(this, ScanPage::class.java)
//            startActivity(intent)
        }
        mainPageBinding.settings.setOnClickListener(){
            val intent = Intent(this,SettingPage::class.java)
            startActivity(intent)

        }
        mainPageBinding.mainLogo.setOnClickListener(){
            reserveBinding.minuteET.setText("00")
            reserveBinding.hourET.setText("00")
            val reserveBuilder = AlertDialog.Builder(this)
                .setView(reserveView)
            if(reserveView.getParent() !=null){
                (reserveView.getParent() as ViewGroup).removeView(reserveView)
            }
            val reserveDialog = reserveBuilder.show()
            reserveBinding.proceed.setOnClickListener() {
                var hour = Integer.parseInt(reserveBinding.hourET.getText().toString())
                var minute = Integer.parseInt(reserveBinding.minuteET.getText().toString())
                var reserveTime = (hour * 60 + minute)*1000
                val handler = Handler()
                val handlerTask = object : Runnable {
                    override fun run() {
                        Toast.makeText(this@MainPage,"Your wine is ready to be served!",Toast.LENGTH_SHORT).show()
                    }
                }
                handler.postDelayed(handlerTask, reserveTime.toLong())
                reserveDialog.dismiss()
            }
            reserveBinding.cancel.setOnClickListener(){
                reserveDialog.dismiss()
            }
        }

    }

    private fun requestCameraAndStartScanner() {
        if (isPermissionGranted(cameraPermission)) {
            startScanner()
        } else {
            requestCameraPermission()
        }
    }

    private fun startScanner() {
        startScanner(this) { barcodes ->
            barcodes.forEach { barcode ->
                when (barcode.valueType) {
                    Barcode.TYPE_URL -> {
                        val dialog = ScanPopup(this@MainPage,"URL",barcode.url.toString())
                        dialog.show()
//                        binding.textViewQrType.text = "URL"
//                        binding.textViewQrContent.text = barcode.url.toString()
                    }
                    Barcode.TYPE_CONTACT_INFO -> {
                        val dialog = ScanPopup(this@MainPage,"CONTACT",barcode.contactInfo.toString())
                        dialog.show()
//                        binding.textViewQrType.text = "Contact"
//                        binding.textViewQrContent.text = barcode.contactInfo.toString()
                    }
                    else -> {
                        val dialog = ScanPopup(this@MainPage,"Other",barcode.rawValue.toString())
                        dialog.show()
//                        val mDialogView = LayoutInflater.from(this).inflate(R.layout.scan_popup, null)
//                        val mBuilder = AlertDialog.Builder(this)
//                            .setView(mDialogView)
//                            .setTitle("Result Form")
//
//                        mBuilder.show()
//                        binding.textViewQrType.text = "Other"
//                        binding.textViewQrContent.text = barcode.rawValue.toString()
                    }
                }
            }
        }
    }

    private fun requestCameraPermission() {
        when {
            shouldShowRequestPermissionRationale(cameraPermission) -> {
                cameraPermissionRequest(
                    positive = { openPermissionSetting() }
                )
            }

            else -> {
                requestPermissionLauncher.launch(cameraPermission)
            }
        }
    }
}