package com.example.smart_winery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

import com.example.smart_winery.databinding.MainPageBinding

@GlideModule
class MyGlide : AppGlideModule()


class MainPage : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val mainPageBinding = MainPageBinding.inflate(layoutInflater)
        setContentView(mainPageBinding.root)

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

    }
}