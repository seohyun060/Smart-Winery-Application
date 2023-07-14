package com.example.smart_winery

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.example.smart_winery.databinding.SettingPageBinding

class SettingPage : AppCompatActivity() {
    private lateinit var binding: SettingPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = SettingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            // RED 온도 증가 및 감소 버튼
            rUp.setOnClickListener(::upDownClicked)
            rDown.setOnClickListener(::upDownClicked)

            // WHITE 온도 증가 및 감소 버튼
            wUp.setOnClickListener(::upDownClicked)
            wDown.setOnClickListener(::upDownClicked)

            // SPARKLE 온도 증가 및 감소 버튼
            sUp.setOnClickListener(::upDownClicked)
            sDown.setOnClickListener(::upDownClicked)

            switch3.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // On 할 때
                    rUp.isEnabled = false
                    rDown.isEnabled = false
                    rTemp.text = "16"
                    floor3.setBackgroundColor(Color.GRAY)
                    state3.setText("Auto     ")
                } else {
                    // Off 할 때
                    rUp.isEnabled = true
                    rDown.isEnabled = true
                    floor3.setBackgroundResource(R.drawable.border_top)
                    state3.setText("Manual   ")
                }
            }

            switch2.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // On 할 때
                    wUp.isEnabled=false
                    wDown.isEnabled=false
                    wTemp.text="13"
                    floor2.setBackgroundColor(Color.GRAY)
                    state2.setText("Auto     ")
                } else {
                    // Off 할 때
                    wUp.isEnabled=true
                    wDown.isEnabled=true
                    floor2.setBackgroundResource(R.drawable.border_top)
                    state2.setText("Manual   ")
                }
            }

            switch1.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // On 할 때
                    sUp.isEnabled=false
                    sDown.isEnabled=false
                    sTemp.text="8"
                    floor1.setBackgroundColor(Color.GRAY)
                    state1.setText("Auto     ")
                } else {
                    // Off 할 때
                    sUp.isEnabled=true
                    sDown.isEnabled=true
                    floor1.setBackgroundResource(R.drawable.border_top_bottom)
                    state1.setText("Manual   ")
                }
            }
        }
    }

    private fun upDownClicked(view: View) {
        when (view.id) {
            R.id.rUp -> {
                var rTempValue: String = binding.rTemp.text.toString()
                var intValue=rTempValue.toInt()
                intValue++
                binding.rTemp.text = intValue.toString()
            }

            R.id.rDown -> {
                var rTempValue: String = binding.rTemp.text.toString()
                var intValue=rTempValue.toInt()
                intValue--
                if (intValue < 0) intValue = 0
                binding.rTemp.text = intValue.toString()
            }

            R.id.wUp -> {
                var wTempValue: String = binding.wTemp.text.toString()
                var intValue=wTempValue.toInt()
                wTempValue = (intValue + 1).toString()
                binding.wTemp.text = wTempValue
            }

            R.id.wDown -> {
                var wTempValue: String = binding.wTemp.text.toString()
                var intValue=wTempValue.toInt()
                intValue--
                if (intValue < 0) intValue = 0
                wTempValue = intValue.toString()
                binding.wTemp.text = wTempValue
            }

            R.id.sUp -> {
                var sTempValue: String = binding.sTemp.text.toString()
                var intValue=sTempValue.toInt()
                intValue++
                binding.sTemp.text = intValue.toString()
            }

            R.id.sDown -> {
                var sTempValue: String = binding.sTemp.text.toString()
                var intValue=sTempValue.toInt()
                intValue--
                if (intValue < 0) intValue = 0
                binding.sTemp.text = intValue.toString()
            }
        }
    }
}
