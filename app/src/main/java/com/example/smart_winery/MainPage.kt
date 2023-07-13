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



    }
}