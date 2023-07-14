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
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.smart_winery.databinding.MainPageBinding
import com.example.smart_winery.databinding.ReserveBinding


@GlideModule
class MyGlide : AppGlideModule()


class MainPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val mainPageBinding = MainPageBinding.inflate(layoutInflater)
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
            val intent = Intent(this, ScanPage::class.java)
            startActivity(intent)
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
}