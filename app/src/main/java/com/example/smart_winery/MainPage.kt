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
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.smart_winery.databinding.MainPageBinding
import com.example.smart_winery.databinding.ReserveBinding
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception


@GlideModule
class MyGlide : AppGlideModule()


class MainPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val mainPageBinding = MainPageBinding.inflate(layoutInflater)
        val reserveBinding = ReserveBinding.inflate(layoutInflater)
        val reserveView = reserveBinding.root
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
        var floor1:JSONObject = JSONObject()
        var floor2:JSONObject = JSONObject()
        var floor3:JSONObject = JSONObject()

        val wineInfo = View.OnClickListener {

        }


        fun displayWine(){

            val floor1wine:JSONArray = floor1.getJSONArray("cell_ids")
            val floor2wine:JSONArray = floor2.getJSONArray("cell_ids")
            val floor3wine:JSONArray = floor3.getJSONArray("cell_ids")


            for ((index,i) in firstfloor.withIndex()){
                for (j in 0 until floor1wine.length()){
                    val wine:JSONObject = floor1wine.getJSONObject(j)
                    if (wine.getInt("col") == index+1){
                        try {
                            GlideApp.with(this)
                                .load(wine.getJSONObject("wine_id").getString("imgsrc"))
                                .into(i)

                        }catch (e:Exception){
                            Log.e("Debug1","No IMG!")
                        }
                    }
                }
                i.clipToOutline = true
            }
            for ((index,i) in secondfloor.withIndex()){
                for (j in 0 until floor2wine.length()){
                    val wine:JSONObject = floor2wine.getJSONObject(j)
                    if (wine.getInt("col") == index+1){
                        try {
                            GlideApp.with(this)
                                .load(wine.getJSONObject("wine_id").getString("imgsrc"))
                                .into(i)

                        }catch (e:Exception){
                            Log.e("Debug2","No $index , $j IMG!")
                        }
                    }
                }
                i.clipToOutline = true
            }
            for ((index,i) in thirdfloor.withIndex()){
                for (j in 0 until floor3wine.length()){
                    val wine:JSONObject = floor3wine.getJSONObject(j)
                    if (wine.getInt("col") == index+1){
                        try {
                            GlideApp.with(this)
                                .load(wine.getJSONObject("wine_id").getString("imgsrc"))
                                .into(i)

                        }catch (e:Exception){
                            Log.e("Debug3","No $index , $j IMG!")
                        }
                    }
                }
                i.clipToOutline = true
            }
        }

        val url = "http://10.0.2.2:3000/winecellar/status?id=64ae2b0848a3d71c485e2472"
//        var url = "http://13.48.52.200:3000/winecellar/status?id=64b4f9a38b4dc227def9b5b1"
        val queue : RequestQueue = Volley.newRequestQueue(applicationContext)
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            floor1 = response.getJSONObject("floor1")
            floor2 = response.getJSONObject("floor2")
            floor3 = response.getJSONObject("floor3")
            displayWine()
            }, { error ->
            Log.e("TAGa", "RESPONSE IS $error")
            // in this case we are simply displaying a toast message.
            Toast.makeText(this@MainPage, "Fail to get response", Toast.LENGTH_SHORT)
                .show()
            })
        queue.add(request)

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
            reserveBinding.minuteET.setText("0")
            reserveBinding.hourET.setText("0")
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