package com.example.smart_winery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.smart_winery.databinding.MainPageBinding


class MainPage : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val mainPageBinding = MainPageBinding.inflate(layoutInflater)

        setContentView(mainPageBinding.root)
        mainPageBinding.addWine.setOnClickListener() {
            val intent = Intent(this, ScanPage::class.java)
            startActivity(intent)
        }
        mainPageBinding.settings.setOnClickListener(){
//            val intent = Intent(this,SettingPage::class.java)
//            startActivity(intent)
            //dfdfsfdsdfsd

        }
    }

}