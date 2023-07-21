package com.example.smart_winery

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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



    var WineList1 : MutableList<WineInfo> = mutableListOf()
    var WineList2 : MutableList<WineInfo> = mutableListOf()
    var WineList3 : MutableList<WineInfo> = mutableListOf()
    var refresh = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = SettingPageBinding.inflate(layoutInflater)
        val mainPageBinding = MainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var url = "http://13.48.52.200:3000/winecellar/status?id=64b4f9a38b4dc227def9b5b1"
        var cellfloor1: JSONObject = JSONObject()
        var cellfloor2:JSONObject = JSONObject()
        var cellfloor3:JSONObject = JSONObject()

        var cell1NowTemp = 16
        var cell1TargetTemp = 16
        var cell2NowTemp = 10
        var cell2TargetTemp = 10
        var cell3NowTemp = 6
        var cell3TargetTemp = 6
        var floor1type = 1
        var floor2type = 3
        var floor3type = 3
        var floor1smart = true
        var floor2smart = true
        var floor3smart = true

        var cell1AdjustTemp = 16
        var cell2AdjustTemp = 10
        var cell3AdjustTemp = 6
        val queue : RequestQueue = Volley.newRequestQueue(applicationContext)
//        when(floor1type) {
//            1 -> {
//                binding.floor1Type.setText("RED")
//                binding.floor1Type.setBackgroundColor(Color.parseColor("#9B1132"))
//            }
//            2 -> {
//                binding.floor1Type.setText("WHITE")
//                binding.floor1Type.setBackgroundColor(Color.parseColor("#CFE449"))
//            }
//            3 -> {
//                binding.floor1Type.setText("SPARKLE")
//                binding.floor1Type.setBackgroundColor(Color.parseColor("#2589FF"))
//            }
//        }
        val typebuttonNumber = binding.floor1Type.id - 1
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            Log.d("responseebal",response.toString())
            cellfloor1 = response.getJSONObject("floor1")
            cellfloor2 = response.getJSONObject("floor2")
            cellfloor3 = response.getJSONObject("floor3")

            val floor1wines: JSONArray = cellfloor1.getJSONArray("cell_ids")
            val floor2wines: JSONArray = cellfloor2.getJSONArray("cell_ids")
            val floor3wines: JSONArray = cellfloor3.getJSONArray("cell_ids")

            cell1NowTemp = cellfloor1.getInt("temperature_target")
            cell1TargetTemp = cellfloor1.getInt("temperature_target")
            cell2NowTemp = cellfloor2.getInt("temperature_now")

             cell2TargetTemp = cellfloor2.getInt("temperature_target")
             cell3NowTemp= cellfloor3.getInt("temperature_now")
             cell3TargetTemp = cellfloor3.getInt("temperature_target")


            floor1type = cellfloor1.getInt("type")
            floor2type = cellfloor2.getInt("type")
            floor3type = cellfloor3.getInt("type")

            floor1smart = cellfloor1.getBoolean("is_smart_mode")
            floor2smart = cellfloor2.getBoolean("is_smart_mode")
            floor3smart = cellfloor3.getBoolean("is_smart_mode")
            for (j in 0 until floor1wines.length()){
                val wine:JSONObject = floor1wines.getJSONObject(j)
                try {
                    val floor1wine = wine.getJSONObject("wine_id")
                    var aromaList: MutableList<AromaInfo> = mutableListOf()
                    var pairingList: MutableList<PairingInfo> = mutableListOf()
                    var wineAromas: JSONArray = JSONArray()
                    var winePairings: JSONArray = JSONArray()
                    try {
                        wineAromas = floor1wine.getJSONArray("aroma")
                    } catch (e: Exception) { }
                    try{
                        winePairings = floor1wine.getJSONArray("pairing")
                    } catch (e:Exception){ }
                    for (k in 0 until wineAromas.length()) {
                        var wineAroma = AromaInfo("", "", "", mutableListOf())
                        var nameList: MutableList<String> = mutableListOf()
                        var aromaNames = wineAromas.getJSONObject(k).getJSONArray("aroma_names")
                        wineAroma.Aroma_Id = wineAromas.getJSONObject(k).getString("_id")
                        wineAroma.Aroma_category =
                            wineAromas.getJSONObject(k).getString("category")
                        wineAroma.Aroma_image =
                            wineAromas.getJSONObject(k).getString("imgsrc")
                        for (l in 0 until aromaNames.length()) {
                            var aromaName: String = aromaNames.getString(l)
                            nameList.add(aromaName)
                        }
                        wineAroma.Aroma_names = nameList
                        aromaList.add(wineAroma)
                    }
                    for (k in 0 until winePairings.length()) {
                        var winePairing = PairingInfo("", "", "", mutableListOf())
                        var nameList: MutableList<String> = mutableListOf()
                        var pairingNames = winePairings.getJSONObject(k).getJSONArray("pairing_names")
                        winePairing.Pairing_Id = winePairings.getJSONObject(k).getString("_id")
                        winePairing.Pairing_category = winePairings.getJSONObject(k).getString("category")
                        winePairing.Pairing_image = winePairings.getJSONObject(k).getString("imgsrc")
                        for (l in 0 until pairingNames.length()) {
                            var pairingName: String = pairingNames.getString(l)
                            nameList.add(pairingName)
                        }
                        winePairing.Pairing_names = nameList
                        pairingList.add(winePairing)
                    }
                    WineList1.add(
                        WineInfo(wine.getInt("row"),
                            wine.getInt("col") - 1,
                            floor1wine.getString("_id"),
                            floor1wine.getString("eng_name"),
                            floor1wine.getString("imgsrc"),
                            floor1wine.getInt("price"),
                            floor1wine.getInt("sweet"),
                            floor1wine.getInt("acid"),
                            floor1wine.getInt("body"),
                            floor1wine.getInt("tannin"),
                            aromaList,
                            floor1wine.getString("alcohol"),
                            floor1wine.getInt("temp"),
                            floor1wine.getString("type"),
                            pairingList
                        )
                    )
                }catch (e:Exception){ }
            }
            for (j in 0 until floor2wines.length()){
                val wine:JSONObject = floor2wines.getJSONObject(j)
                try {
                    val floor2wine = wine.getJSONObject("wine_id")
                    var aromaList: MutableList<AromaInfo> = mutableListOf()
                    var pairingList: MutableList<PairingInfo> = mutableListOf()
                    var wineAromas: JSONArray = JSONArray()
                    var winePairings: JSONArray = JSONArray()
                    try {
                        wineAromas = floor2wine.getJSONArray("aroma")
                    } catch (e: Exception) { }
                    try{
                        winePairings = floor2wine.getJSONArray("pairing")
                    } catch (e:Exception){ }
                    for (k in 0 until wineAromas.length()) {
                        var wineAroma = AromaInfo("", "", "", mutableListOf())
                        var nameList: MutableList<String> = mutableListOf()
                        var aromaNames = wineAromas.getJSONObject(k).getJSONArray("aroma_names")
                        wineAroma.Aroma_Id = wineAromas.getJSONObject(k).getString("_id")
                        wineAroma.Aroma_category =
                            wineAromas.getJSONObject(k).getString("category")
                        wineAroma.Aroma_image =
                            wineAromas.getJSONObject(k).getString("imgsrc")
                        for (l in 0 until aromaNames.length()) {
                            var aromaName: String = aromaNames.getString(l)
                            nameList.add(aromaName)
                        }
                        wineAroma.Aroma_names = nameList
                        aromaList.add(wineAroma)
                    }
                    for (k in 0 until winePairings.length()) {
                        var winePairing = PairingInfo("", "", "", mutableListOf())
                        var nameList: MutableList<String> = mutableListOf()
                        var pairingNames = winePairings.getJSONObject(k).getJSONArray("pairing_names")
                        winePairing.Pairing_Id = winePairings.getJSONObject(k).getString("_id")
                        winePairing.Pairing_category = winePairings.getJSONObject(k).getString("category")
                        winePairing.Pairing_image = winePairings.getJSONObject(k).getString("imgsrc")
                        for (l in 0 until pairingNames.length()) {
                            var pairingName: String = pairingNames.getString(l)
                            nameList.add(pairingName)
                        }
                        winePairing.Pairing_names = nameList
                        pairingList.add(winePairing)
                    }
                    WineList2.add(
                        WineInfo(wine.getInt("row"),
                            wine.getInt("col") - 1,
                            floor2wine.getString("_id"),
                            floor2wine.getString("eng_name"),
                            floor2wine.getString("imgsrc"),
                            floor2wine.getInt("price"),
                            floor2wine.getInt("sweet"),
                            floor2wine.getInt("acid"),
                            floor2wine.getInt("body"),
                            floor2wine.getInt("tannin"),
                            aromaList,
                            floor2wine.getString("alcohol"),
                            floor2wine.getInt("temp"),
                            floor2wine.getString("type"),
                            pairingList
                        )
                    )
                }catch (e:Exception){ }
            }
            for (j in 0 until floor3wines.length()){
                val wine:JSONObject = floor3wines.getJSONObject(j)
                try {
                    val floor3wine = wine.getJSONObject("wine_id")
                    var aromaList: MutableList<AromaInfo> = mutableListOf()
                    var pairingList: MutableList<PairingInfo> = mutableListOf()
                    var wineAromas: JSONArray = JSONArray()
                    var winePairings: JSONArray = JSONArray()
                    try {
                        wineAromas = floor3wine.getJSONArray("aroma")
                    } catch (e: Exception) { }
                    try{
                        winePairings = floor3wine.getJSONArray("pairing")
                    } catch (e:Exception){ }
                    for (k in 0 until wineAromas.length()) {
                        var wineAroma = AromaInfo("", "", "", mutableListOf())
                        var nameList: MutableList<String> = mutableListOf()
                        var aromaNames = wineAromas.getJSONObject(k).getJSONArray("aroma_names")
                        wineAroma.Aroma_Id = wineAromas.getJSONObject(k).getString("_id")
                        wineAroma.Aroma_category =
                            wineAromas.getJSONObject(k).getString("category")
                        wineAroma.Aroma_image =
                            wineAromas.getJSONObject(k).getString("imgsrc")
                        for (l in 0 until aromaNames.length()) {
                            var aromaName: String = aromaNames.getString(l)
                            nameList.add(aromaName)
                        }
                        wineAroma.Aroma_names = nameList
                        aromaList.add(wineAroma)
                    }
                    for (k in 0 until winePairings.length()) {
                        var winePairing = PairingInfo("", "", "", mutableListOf())
                        var nameList: MutableList<String> = mutableListOf()
                        var pairingNames = winePairings.getJSONObject(k).getJSONArray("pairing_names")
                        winePairing.Pairing_Id = winePairings.getJSONObject(k).getString("_id")
                        winePairing.Pairing_category = winePairings.getJSONObject(k).getString("category")
                        winePairing.Pairing_image = winePairings.getJSONObject(k).getString("imgsrc")
                        for (l in 0 until pairingNames.length()) {
                            var pairingName: String = pairingNames.getString(l)
                            nameList.add(pairingName)
                        }
                        winePairing.Pairing_names = nameList
                        pairingList.add(winePairing)
                    }
                    WineList3.add(
                        WineInfo(wine.getInt("row"),
                            wine.getInt("col") - 1,
                            floor3wine.getString("_id"),
                            floor3wine.getString("eng_name"),
                            floor3wine.getString("imgsrc"),
                            floor3wine.getInt("price"),
                            floor3wine.getInt("sweet"),
                            floor3wine.getInt("acid"),
                            floor3wine.getInt("body"),
                            floor3wine.getInt("tannin"),
                            aromaList,
                            floor3wine.getString("alcohol"),
                            floor3wine.getInt("temp"),
                            floor3wine.getString("type"),
                            pairingList
                        )
                    )
                }catch (e:Exception){ }
            }

            Log.d("floortype2",floor2type.toString())
            Log.d("floortype3",WineList1.size.toString())
        }, { error ->
            Log.e("TAGa", "RESPONSE IS $error")
            // in this case we are simply displaying a toast message.
            Toast.makeText(this@SettingPage, "Fail to get response", Toast.LENGTH_SHORT)
                .show()
        })

        queue.add(request)

//        if (!refresh)
//        {
//            refresh = true
//            finish()
//            overridePendingTransition(0, 0) //인텐트 효과 없애기
//            val intent = intent //인텐트
//            startActivity(intent) //액티비티 열기
//            overridePendingTransition(0, 0) //인텐트 효과 없애기
//        }
        cell1AdjustTemp = cell1TargetTemp
        cell2AdjustTemp = cell2TargetTemp
        cell3AdjustTemp = cell3TargetTemp
        Log.d("floortype",floor2type.toString())
        Log.d("floortype0",WineList1.size.toString())
        initSettingPage()
        //setCellInfo(cell1TargetTemp,cell2TargetTemp,cell3TargetTemp,floor1type,floor2type,floor3type,floor1smart,floor2smart,floor3smart)
        val handler = Handler()
        var time = 1000
        val handlerTask = object : Runnable {
            override fun run() {
                setCellInfo(cell1TargetTemp,cell2TargetTemp,cell3TargetTemp,floor1type,floor2type,floor3type,floor1smart,floor2smart,floor3smart)
                binding.switch3.isChecked = !floor3smart
                binding.switch2.isChecked = !floor2smart
                binding.switch1.isChecked = !floor1smart
            }
        }
        handler.postDelayed(handlerTask, time.toLong())

        val upDownListener = object :View.OnClickListener {
            override fun onClick(view:View) {
                when (view.id) {
                    R.id.up1 -> {
                        var rTempValue: String = binding.temp1.text.toString()
                        var intValue=rTempValue.toInt()
                        intValue++
                        cell1AdjustTemp= intValue
                        binding.temp1.text = intValue.toString()
                    }

                    R.id.down1 -> {
                        var rTempValue: String = binding.temp1.text.toString()
                        var intValue=rTempValue.toInt()
                        intValue--
                        if (intValue < 0) intValue = 0
                        cell1AdjustTemp= intValue
                        binding.temp1.text = intValue.toString()
                    }

                    R.id.up2 -> {
                        var wTempValue: String = binding.temp2.text.toString()
                        var intValue=wTempValue.toInt()
                        wTempValue = (intValue + 1).toString()
                        cell2AdjustTemp= intValue +1
                        binding.temp2.text = wTempValue
                    }

                    R.id.down2 -> {
                        var wTempValue: String = binding.temp2.text.toString()
                        var intValue=wTempValue.toInt()
                        intValue--
                        if (intValue < 0) intValue = 0
                        cell2AdjustTemp= intValue
                        wTempValue = intValue.toString()
                        binding.temp2.text = wTempValue
                    }

                    R.id.up3 -> {
                        var sTempValue: String = binding.temp3.text.toString()
                        var intValue=sTempValue.toInt()
                        intValue++
                        cell3AdjustTemp= intValue
                        binding.temp3.text = intValue.toString()
                    }

                    R.id.down3 -> {
                        var sTempValue: String = binding.temp3.text.toString()
                        var intValue=sTempValue.toInt()
                        intValue--
                        if (intValue < 0) intValue = 0
                        cell3AdjustTemp= intValue
                        binding.temp3.text = intValue.toString()
                    }
                }
            }
        }
        val typeButtonListener = object : View.OnClickListener {
            override fun onClick (v:View?) {
                val clickedFloorButton = v?.id.toString().toInt() - typebuttonNumber
                Log.d("floortype",floor2type.toString())
                Log.d("floortype0",WineList1.size.toString())
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
                setCellInfo(cell1TargetTemp,cell2TargetTemp,cell3TargetTemp,floor1type,floor2type,floor3type,floor1smart,floor2smart,floor3smart)
            }
        }
        binding.apply {
            // RED 온도 증가 및 감소 버튼
            apply.setOnClickListener() {
                val postSettingQueue : RequestQueue = Volley.newRequestQueue(this@SettingPage)
                val postSettingURL = "http://13.48.52.200:3000/winecellar/setting"
                val postSettingReq = """
                                                {
                                                    "cellarid": "64b4f9a38b4dc227def9b5b1",
                                                    "floor1_type": ${floor1type},
                                                    "floor1_temperature_target": ${cell1AdjustTemp},
                                                    "floor1_is_smart_mode": ${floor1smart},
                                                    "floor2_type": ${floor2type},
                                                     "floor2_temperature_target": ${cell2AdjustTemp},                                                   
                                                     "floor2_is_smart_mode": ${floor2smart},
                                                     "floor3_type": ${floor3type},
                                                     "floor3_temperature_target": ${cell3AdjustTemp},                                                   
                                                     "floor3_is_smart_mode": ${floor3smart}                                                                                               
                                                }
                                                """.trimIndent()
                var post_setting_data:JSONObject = JSONObject(postSettingReq)
                val settingRequest = JsonObjectRequest(Request.Method.POST, postSettingURL, post_setting_data, { response ->
                }, { error ->
                    Log.e("TAGa", "RESPONSE IS $error")
                    // in this case we are simply displaying a toast message.
                    Toast.makeText(this@SettingPage, "Fail to get response", Toast.LENGTH_SHORT)
                        .show()
                })
                postSettingQueue.add(settingRequest)
                finish()
            }
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
                                binding.temp3.setText("${cell3TargetTemp}")
                            }
                            "White" -> {
                                floor3type = 2
                                binding.floor3Type.setText("WHITE")
                                binding.floor3Type.setBackgroundColor(Color.parseColor("#CFE449"))
                                binding.temp3.setText("${cell3TargetTemp}")
                            }
                            "Sparkling" -> {
                                floor3type = 3
                                binding.floor3Type.setText("SPARKLE")
                                binding.floor3Type.setBackgroundColor(Color.parseColor("#2589FF"))
                                binding.temp3.setText("${cell3TargetTemp}")
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
                    binding.temp3.setText("${cell3TargetTemp}")
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
                                binding.temp2.setText("${cell2TargetTemp}")
                            }
                            "White" -> {
                                floor2type = 2
                                binding.floor2Type.setText("WHITE")
                                binding.floor2Type.setBackgroundColor(Color.parseColor("#CFE449"))
                                binding.temp2.setText("${cell2TargetTemp}")
                            }
                            "Sparkling" -> {
                                floor2type = 3
                                binding.floor2Type.setText("SPARKLE")
                                binding.floor2Type.setBackgroundColor(Color.parseColor("#2589FF"))
                                binding.temp2.setText("${cell2TargetTemp}")
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
                    binding.temp2.setText("${cell2TargetTemp}")
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
                                binding.temp1.setText("${cell1TargetTemp}")
                            }
                            "White" -> {
                                floor1type = 2
                                binding.floor1Type.setText("WHITE")
                                binding.floor1Type.setBackgroundColor(Color.parseColor("#CFE449"))
                                binding.temp1.setText("${cell1TargetTemp}")
                            }
                            "Sparkling" -> {
                                floor1type = 3
                                binding.floor1Type.setText("SPARKLE")
                                binding.floor1Type.setBackgroundColor(Color.parseColor("#2589FF"))
                                binding.temp1.setText("${cell1TargetTemp}")
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

                    binding.temp1.setText("${cell1TargetTemp}")
                    binding.floor1Type.setBackgroundColor(Color.parseColor("#888888"))
                    floor1Type.isEnabled = false
                    updown1.background = getDrawable(R.drawable.timer_border)
                    //floor2.setBackgroundResource(R.drawable.border_top)
                    //floor1.setBackgroundResource(R.drawable.border_top_bottom)
                    state1.setText("User")

                }
            }
            up1.setOnClickListener(upDownListener)
            up2.setOnClickListener(upDownListener)
            up3.setOnClickListener(upDownListener)
            down1.setOnClickListener(upDownListener)
            down2.setOnClickListener(upDownListener)
            down3.setOnClickListener(upDownListener)

            floor1Type.setOnClickListener(typeButtonListener)
            floor2Type.setOnClickListener(typeButtonListener)
            floor3Type.setOnClickListener(typeButtonListener)
        }
    }
    fun initSettingPage() {
        binding.floor1Type.setText("")
        binding.floor2Type.setText("")
        binding.floor3Type.setText("")
        binding.floor1Type.setBackgroundColor(Color.parseColor("#888888"))
        binding.floor1Type.setBackgroundColor(Color.parseColor("#888888"))
        binding.floor2Type.setBackgroundColor(Color.parseColor("#888888"))
        binding.floor3Type.setBackgroundColor(Color.parseColor("#888888"))
        binding.temp1.setText("")
        binding.temp2.setText("")
        binding.temp3.setText("")
    }
    public fun setCellInfo (cell1TargetTemp : Int
                     , cell2TargetTemp : Int
                     , cell3TargetTemp:Int
                    ,floor1type: Int
                     ,floor2type: Int
                     ,floor3type: Int
                    ,floor1smart:Boolean
                     ,floor2smart:Boolean
                     ,floor3smart:Boolean
                        ) {
        if (floor1smart) {
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
            binding.temp1.setText("${cell1TargetTemp}")
        }
        else {
            binding.floor1Type.setText("USER")
            binding.floor1Type.setBackgroundColor(Color.parseColor("#888888"))
        }
        if (floor2smart) {
            when(floor2type) {
                1 -> {
                    binding.floor2Type.setText("RED")
                    binding.floor2Type.setBackgroundColor(Color.parseColor("#9B1132"))
                }
                2 -> {
                    binding.floor2Type.setText("WHITE")
                    binding.floor2Type.setBackgroundColor(Color.parseColor("#CFE449"))
                }
                3 -> {
                    binding.floor2Type.setText("SPARKLE")
                    binding.floor2Type.setBackgroundColor(Color.parseColor("#2589FF"))
                }
            }
            binding.temp2.setText("${cell2TargetTemp}")
        }
        else {
            binding.floor2Type.setText("USER")
            binding.floor2Type.setBackgroundColor(Color.parseColor("#888888"))
        }
        if (floor3smart) {
            Log.d("winelist3",WineList3.size.toString())
            Log.d("winelist2",WineList2.size.toString())
            Log.d("winelist1",WineList1.size.toString())
            when(floor3type) {
                1 -> {
                    binding.floor3Type.setText("RED")
                    binding.floor3Type.setBackgroundColor(Color.parseColor("#9B1132"))
                }
                2 -> {
                    binding.floor3Type.setText("WHITE")
                    binding.floor3Type.setBackgroundColor(Color.parseColor("#CFE449"))
                }
                3 -> {
                    binding.floor3Type.setText("SPARKLE")
                    binding.floor3Type.setBackgroundColor(Color.parseColor("#2589FF"))
                }
            }
            binding.temp3.setText("${cell3TargetTemp}")
        }
        else {
            binding.floor3Type.setText("USER")
            binding.floor3Type.setBackgroundColor(Color.parseColor("#888888"))
        }
    }


}

