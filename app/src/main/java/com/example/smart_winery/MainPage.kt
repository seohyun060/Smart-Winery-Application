package com.example.smart_winery

import android.content.DialogInterface
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.smart_winery.databinding.MainPageBinding
import com.example.smart_winery.databinding.ReserveBinding

import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask


class MainPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val mainPageBinding = MainPageBinding.inflate(layoutInflater)
        val reserveBinding = ReserveBinding.inflate(layoutInflater)
        val reserveView = reserveBinding.root

        val firstfloor = arrayListOf<ImageButton>(
            findViewById(R.id.btn11),
            findViewById(R.id.btn12),
            findViewById(R.id.btn13),
            findViewById(R.id.btn14),
            findViewById(R.id.btn15)
        )
        val secondfloor = arrayListOf<ImageButton>(
            findViewById(R.id.btn21),
            findViewById(R.id.btn22),
            findViewById(R.id.btn23),
            findViewById(R.id.btn24),
            findViewById(R.id.btn25)
        )
        val thirdfloor = arrayListOf<ImageButton>(
            findViewById(R.id.btn31),
            findViewById(R.id.btn32),
            findViewById(R.id.btn33),
            findViewById(R.id.btn34),
            findViewById(R.id.btn35)
        )



        setContentView(mainPageBinding.root)
        mainPageBinding.addWine.setOnClickListener() {
            val intent = Intent(this, ScanPage::class.java)
            startActivity(intent)
        }
        mainPageBinding.settings.setOnClickListener(){
            //val intent = Intent(this,SettingPage::class.java)
            //startActivity(intent)
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