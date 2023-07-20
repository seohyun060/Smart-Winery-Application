package com.example.smart_winery

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.example.smart_winery.databinding.MainPageBinding
import com.example.smart_winery.databinding.SettingPageBinding

class SettingPage : AppCompatActivity() {
    private lateinit var binding: SettingPageBinding

    var floor1type = MainPage().floor1type
    var floor2type = MainPage().floor2type
    var floor3type = MainPage().floor3type
    var WineList1 = MainPage().WineList1
    var WineList2= MainPage().WineList2
    var WineList3 = MainPage().WineList3
    var floor1smart = MainPage().floor1smart
    var floor2smart = MainPage().floor2smart
    var floor3smart = MainPage().floor3smart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = SettingPageBinding.inflate(layoutInflater)
        val mainPageBinding = MainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        var url = "http://13.48.52.200:3000/winecellar/status?id=64b4f9a38b4dc227def9b5b1"
//        var cellfloor1:JSONObject = JSONObject()
//        var cellfloor2:JSONObject = JSONObject()
//        var cellfloor3:JSONObject = JSONObject()
////        val floor1wines: JSONArray = cellfloor1.getJSONArray("cell_ids")
////        val floor2wines: JSONArray = cellfloor2.getJSONArray("cell_ids")
////        val floor3wines: JSONArray = cellfloor3.getJSONArray("cell_ids")
//        val queue : RequestQueue = Volley.newRequestQueue(applicationContext)
        when(floor1type) {
            1 -> {
                binding.floor1Type.setText("RED")
                binding.floor1Type.setBackgroundColor(Color.parseColor("#9B1132"))
            }
            2 -> {
                binding.floor1Type.setText("WHITE")
                binding.floor1Type.setBackgroundColor(Color.parseColor("#CFE449"))
            }
            3 -> {
                binding.floor1Type.setText("SPARKLE")
                binding.floor1Type.setBackgroundColor(Color.parseColor("#2589FF"))
            }
        }
        val typebuttonNumber = 2131231315
//        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
//            Log.d("responseebal",response.toString())
//            cellfloor1 = response.getJSONObject("floor1")
//            cellfloor2 = response.getJSONObject("floor2")
//            cellfloor3 = response.getJSONObject("floor3")
//        }, { error ->
//            Log.e("TAGa", "RESPONSE IS $error")
//            // in this case we are simply displaying a toast message.
//            Toast.makeText(this@SettingPage, "Fail to get response", Toast.LENGTH_SHORT)
//                .show()
//        })
//        queue.add(request)
        setCellInfo()
        binding.apply {
            // RED 온도 증가 및 감소 버튼
            up1.setOnClickListener(::upDownClicked)
            down1.setOnClickListener(::upDownClicked)

            // WHITE 온도 증가 및 감소 버튼
            up2.setOnClickListener(::upDownClicked)
            down2.setOnClickListener(::upDownClicked)

            // SPARKLE 온도 증가 및 감소 버튼
            up3.setOnClickListener(::upDownClicked)
            down3.setOnClickListener(::upDownClicked)

            switch3.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) {
                    var changeable = true
                    var firstWineType = ""
                    if (WineList3.size !=0)
                    {
                        firstWineType = WineList3[0].Wine_Type
                        for (wine in WineList3){
                            if (wine.Wine_Type != firstWineType){
                                changeable= false
                                break
                            }
                        }
                    }
                    else {
                        binding.floor3Type.setText("SPARKLE")
                        binding.floor3Type.setBackgroundColor(Color.parseColor("#2589FF"))
                        binding.temp3.setText("6")
                    }
                    if (changeable)
                    {
                        up3.isEnabled=false
                        down3.isEnabled=false
                        floor3Type.isEnabled=true
                        floor3smart = true
                        updown3.background = getDrawable(R.drawable.border_blackout)
                        state3.setText("Auto")

                        when(firstWineType){
                            "Red" -> {
                                floor3type = 1
                                binding.floor3Type.setText("RED")
                                binding.floor3Type.setBackgroundColor(Color.parseColor("#9B1132"))
                                binding.temp3.setText("16")
                            }
                            "White" -> {
                                floor3type = 2
                                binding.floor3Type.setText("WHITE")
                                binding.floor3Type.setBackgroundColor(Color.parseColor("#CFE449"))
                                binding.temp3.setText("10")
                            }
                            "Sparkling" -> {
                                floor3type = 3
                                binding.floor3Type.setText("SPARKLE")
                                binding.floor3Type.setBackgroundColor(Color.parseColor("#2589FF"))
                                binding.temp3.setText("6")
                            }
                        }
                    }
                    // On 할 때
                    else{

                    }
                } else {
                    // Off 할 때
                    up3.isEnabled = true
                    down3.isEnabled = true
                    binding.floor3Type.setText("USER")
                    floor3Type.isEnabled = false
                    floor3smart = false
                    binding.floor3Type.setBackgroundColor(Color.parseColor("#888888"))
                    updown3.background = getDrawable(R.drawable.timer_border)
                    state3.setText("User")
                }
            }

            switch2.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) {
                    var changeable = true
                    var firstWineType = ""
                    if (WineList2.size !=0)
                    {
                        firstWineType = WineList2[0].Wine_Type
                        for (wine in WineList2){
                            if (wine.Wine_Type != firstWineType){
                                changeable= false
                                break
                            }
                        }
                    }
                    else {
                        floor2type = 2
                        binding.floor2Type.setText("WHITE")
                        binding.floor2Type.setBackgroundColor(Color.parseColor("#CFE449"))
                        binding.temp2.setText("10")
                    }
                    if (changeable)
                    {
                        up2.isEnabled=false
                        down2.isEnabled=false
                        floor2smart = true
                        floor2Type.isEnabled=true
                        updown2.background = getDrawable(R.drawable.border_blackout)
                        state2.setText("Auto")

                        when(firstWineType){
                            "Red" -> {
                                floor2type = 1
                                binding.floor2Type.setText("RED")
                                binding.floor2Type.setBackgroundColor(Color.parseColor("#9B1132"))
                                binding.temp2.setText("16")
                            }
                            "White" -> {
                                floor2type = 2
                                binding.floor2Type.setText("WHITE")
                                binding.floor2Type.setBackgroundColor(Color.parseColor("#CFE449"))
                                binding.temp2.setText("10")
                            }
                            "Sparkling" -> {
                                floor2type = 3
                                binding.floor2Type.setText("SPARKLE")
                                binding.floor2Type.setBackgroundColor(Color.parseColor("#2589FF"))
                                binding.temp2.setText("6")
                            }
                        }
                    }
                    // On 할 때
                    else{

                    }
                } else {
                    // Off 할 때
                    up2.isEnabled=true
                    down2.isEnabled=true
                    floor2smart =false
                    binding.floor2Type.setText("USER")
                    floor2Type.isEnabled = false
                    floor2smart = false
                    binding.floor2Type.setBackgroundColor(Color.parseColor("#888888"))
                    updown2.background = getDrawable(R.drawable.timer_border)
                    //floor2.setBackgroundResource(R.drawable.border_top)
                    state2.setText("User")
                }
            }

            switch1.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) {
                    Log.d("SIBAL","")
                    var changeable = true
                    var firstWineType = ""
                    if (WineList1.size !=0)
                    {
                        firstWineType = WineList1[0].Wine_Type
                        for (wine in WineList1){
                            if (wine.Wine_Type != firstWineType){
                                changeable= false
                                break
                            }
                        }
                    }
                    else {
                        floor1type = 1
                        binding.floor1Type.setText("RED")
                        binding.floor1Type.setBackgroundColor(Color.parseColor("#9B1132"))
                        binding.temp1.setText("16")
                    }

                    if (changeable)
                    {
                        up1.isEnabled=false
                        floor1smart = true
                        down1.isEnabled=false
                        floor1Type.isEnabled=true
                        updown1.background = getDrawable(R.drawable.border_blackout)
                        state1.setText("Auto")

                        when(firstWineType){
                            "Red" -> {
                                floor1type = 1
                                binding.floor1Type.setText("RED")
                                binding.floor1Type.setBackgroundColor(Color.parseColor("#9B1132"))
                                binding.temp1.setText("16")
                            }
                            "White" -> {
                                floor1type = 2
                                binding.floor1Type.setText("WHITE")
                                binding.floor1Type.setBackgroundColor(Color.parseColor("#CFE449"))
                                binding.temp1.setText("10")
                            }
                            "Sparkling" -> {
                                floor1type = 3
                                binding.floor1Type.setText("SPARKLE")
                                binding.floor1Type.setBackgroundColor(Color.parseColor("#2589FF"))
                                binding.temp1.setText("6")
                            }
                        }
                    }
                    // On 할 때
                    else{

                    }
                } else {
                    // Off 할 때

                    up1.isEnabled=true
                    down1.isEnabled=true
                    floor1smart = false
                    binding.floor1Type.setText("USER")
                    floor1smart = false
                    binding.floor1Type.setBackgroundColor(Color.parseColor("#888888"))
                    floor1Type.isEnabled = false
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
                when (clickedFloorButton) {
                    1 -> {
                        Log.d("floortype",floor1type.toString())
                        if(WineList1.size ==0 && (binding.state1.text == "Auto")){
                            when(floor1type)
                            {

                                1 ->  floor1type = 2
                                2 -> floor1type = 3
                                3 -> floor1type = 1
                            }
                            Log.d("floortype2",floor1type.toString())
                        }

                    }
                    2 -> {
                        if(WineList2.size ==0 && (binding.state2.text == "Auto")){
                            when(floor2type)
                            {
                                1 -> floor2type = 2
                                2 -> floor2type = 3
                                3 -> floor2type = 1
                            }
                        }
                    }
                    3 -> {
                        if(WineList3.size ==0 && (binding.state3.text == "Auto")){
                            when(floor3type)
                            {
                                1 -> floor3type = 2
                                2 -> floor3type = 3
                                3 -> floor3type = 1
                            }
                        }
                    }
                }
                setCellInfo()
            }
        }
        binding.floor1Type.setOnClickListener(typeButtonListener)
        binding.floor2Type.setOnClickListener(typeButtonListener)
        binding.floor3Type.setOnClickListener(typeButtonListener)
    }
    fun setCellInfo () {
        if (floor1smart && WineList1.size == 0) {
            when(floor1type) {
                1 -> {
                    binding.floor1Type.setText("RED")
                    binding.floor1Type.setBackgroundColor(Color.parseColor("#9B1132"))
                    binding.temp1.setText("16")
                }
                2 -> {
                    binding.floor1Type.setText("WHITE")
                    binding.floor1Type.setBackgroundColor(Color.parseColor("#CFE449"))
                    binding.temp1.setText("10")
                }
                3 -> {
                    binding.floor1Type.setText("SPARKLE")
                    binding.floor1Type.setBackgroundColor(Color.parseColor("#2589FF"))
                    binding.temp1.setText("6")
                }
            }
        }
        else {
            binding.floor1Type.setText("USER")
            binding.floor1Type.setBackgroundColor(Color.parseColor("#888888"))
        }
        if (floor2smart && WineList2.size == 0) {
            when(floor2type) {
                1 -> {
                    binding.floor2Type.setText("RED")
                    binding.floor2Type.setBackgroundColor(Color.parseColor("#9B1132"))
                    binding.temp2.setText("16")
                }
                2 -> {
                    binding.floor2Type.setText("WHITE")
                    binding.floor2Type.setBackgroundColor(Color.parseColor("#CFE449"))
                    binding.temp2.setText("10")
                }
                3 -> {
                    binding.floor2Type.setText("SPARKLE")
                    binding.floor2Type.setBackgroundColor(Color.parseColor("#2589FF"))
                    binding.temp2.setText("6")
                }
            }
        }
        else {
            binding.floor2Type.setText("USER")
            binding.floor2Type.setBackgroundColor(Color.parseColor("#888888"))
        }
        if (floor3smart && WineList3.size == 0) {
            Log.d("winelist3",WineList3.size.toString())
            Log.d("winelist1",WineList1.size.toString())
            when(floor3type) {
                1 -> {
                    binding.floor3Type.setText("RED")
                    binding.floor3Type.setBackgroundColor(Color.parseColor("#9B1132"))
                    binding.temp3.setText("16")
                }
                2 -> {
                    binding.floor3Type.setText("WHITE")
                    binding.floor3Type.setBackgroundColor(Color.parseColor("#CFE449"))
                    binding.temp3.setText("10")
                }
                3 -> {
                    binding.floor3Type.setText("SPARKLE")
                    binding.floor3Type.setBackgroundColor(Color.parseColor("#2589FF"))
                    binding.temp3.setText("6")
                }
            }
        }
        else {
            binding.floor3Type.setText("USER")
            binding.floor3Type.setBackgroundColor(Color.parseColor("#888888"))
        }
    }
    private fun upDownClicked(view: View) {
        when (view.id) {
            R.id.up1 -> {
                var rTempValue: String = binding.temp1.text.toString()
                var intValue=rTempValue.toInt()
                intValue++
                binding.temp1.text = intValue.toString()
            }

            R.id.down1 -> {
                var rTempValue: String = binding.temp1.text.toString()
                var intValue=rTempValue.toInt()
                intValue--
                if (intValue < 0) intValue = 0
                binding.temp1.text = intValue.toString()
            }

            R.id.up2 -> {
                var wTempValue: String = binding.temp2.text.toString()
                var intValue=wTempValue.toInt()
                wTempValue = (intValue + 1).toString()
                binding.temp2.text = wTempValue
            }

            R.id.down2 -> {
                var wTempValue: String = binding.temp2.text.toString()
                var intValue=wTempValue.toInt()
                intValue--
                if (intValue < 0) intValue = 0
                wTempValue = intValue.toString()
                binding.temp2.text = wTempValue
            }

            R.id.up3 -> {
                var sTempValue: String = binding.temp3.text.toString()
                var intValue=sTempValue.toInt()
                intValue++
                binding.temp3.text = intValue.toString()
            }

            R.id.down3 -> {
                var sTempValue: String = binding.temp3.text.toString()
                var intValue=sTempValue.toInt()
                intValue--
                if (intValue < 0) intValue = 0
                binding.temp3.text = intValue.toString()
            }
        }
    }
}
