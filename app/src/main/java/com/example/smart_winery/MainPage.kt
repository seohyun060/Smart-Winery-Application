package com.example.smart_winery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.smart_winery.databinding.MainPageBinding


class MainPage : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val binding = MainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}