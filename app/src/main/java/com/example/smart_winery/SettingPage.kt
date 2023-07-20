package com.example.smart_winery

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.smart_winery.databinding.MainPageBinding
import com.example.smart_winery.databinding.SettingPageBinding
import org.json.JSONArray
import org.json.JSONObject

class SettingPage : AppCompatActivity() {
    private lateinit var binding: SettingPageBinding
    var floor1type = MainPage().floor1type
    var floor2type = MainPage().floor2type
    var floor3type = MainPage().floor3type
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = SettingPageBinding.inflate(layoutInflater)
        val mainPageBinding = MainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var url = "http://13.48.52.200:3000/winecellar/status?id=64b4f9a38b4dc227def9b5b1"
        var cellfloor1:JSONObject = JSONObject()
        var cellfloor2:JSONObject = JSONObject()
        var cellfloor3:JSONObject = JSONObject()
//        val floor1wines: JSONArray = cellfloor1.getJSONArray("cell_ids")
//        val floor2wines: JSONArray = cellfloor2.getJSONArray("cell_ids")
//        val floor3wines: JSONArray = cellfloor3.getJSONArray("cell_ids")
        val queue : RequestQueue = Volley.newRequestQueue(applicationContext)

        val typebuttonNumber = 2131231315
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            Log.d("responseebal",response.toString())
            cellfloor1 = response.getJSONObject("floor1")
            cellfloor2 = response.getJSONObject("floor2")
            cellfloor3 = response.getJSONObject("floor3")
        }, { error ->
            Log.e("TAGa", "RESPONSE IS $error")
            // in this case we are simply displaying a toast message.
            Toast.makeText(this@SettingPage, "Fail to get response", Toast.LENGTH_SHORT)
                .show()
        })
        queue.add(request)
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
                    updown3.background = getDrawable(R.drawable.border_blackout)
                    state3.setText("Auto")
                } else {
                    // Off 할 때
                    rUp.isEnabled = true
                    rDown.isEnabled = true
                    updown3.background = getDrawable(R.drawable.timer_border)
                    state3.setText("User")
                }
            }

            switch2.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // On 할 때
                    wUp.isEnabled=false
                    wDown.isEnabled=false
                    updown2.background = getDrawable(R.drawable.border_blackout)

                    wTemp.text="13"
                    //floor2.setBackgroundColor(Color.GRAY)
                    state2.setText("Auto")
                } else {
                    // Off 할 때
                    wUp.isEnabled=true
                    wDown.isEnabled=true
                    updown2.background = getDrawable(R.drawable.timer_border)
                    //floor2.setBackgroundResource(R.drawable.border_top)
                    state2.setText("User")
                }
            }

            switch1.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // On 할 때
                    sUp.isEnabled=false
                    sDown.isEnabled=false
                    sTemp.text="8"
                    updown1.background = getDrawable(R.drawable.border_blackout)
                    //floor1.setBackgroundColor(Color.GRAY)
                    state1.setText("Auto")
                } else {
                    // Off 할 때
                    sUp.isEnabled=true
                    sDown.isEnabled=true
                    updown1.background = getDrawable(R.drawable.timer_border)
                    //floor2.setBackgroundResource(R.drawable.border_top)
                    //floor1.setBackgroundResource(R.drawable.border_top_bottom)
                    state1.setText("User")
                }
            }
        }
        val typeButtonListener = object : View.OnClickListener {
            override fun onClick (v:View?) {
                val clickedFloorButton = v?.id.toString().toInt() - typebuttonNumber

                if (clickedFloorButton == 1){
                    if(cellfloor1.getJSONArray("cell_ids").length()==0 && (binding.state1.text == "Auto")){
                        if(binding.floor1Type.text == "RED"){
                            binding.floor1Type.setText("WHITE")
                            binding.floor1Type.setBackgroundColor(Color.parseColor("#CFE449"))
                            mainPageBinding.floor1Type.background = getDrawable(R.drawable.white_back)
                        }
                    }
                }
            }
        }
        binding.floor1Type.setOnClickListener(typeButtonListener)
        binding.floor2Type.setOnClickListener(typeButtonListener)
        binding.floor3Type.setOnClickListener(typeButtonListener)
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
