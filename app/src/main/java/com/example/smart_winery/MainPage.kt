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
import androidx.activity.result.contract.ActivityResultContracts
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.smart_winery.databinding.MainPageBinding
import com.example.smart_winery.databinding.ReserveBinding
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

import com.example.smart_winery.ScanPage.Companion.startScanner
import com.example.smart_winery.databinding.WineInfoBinding
import com.google.mlkit.vision.barcode.common.Barcode

@GlideModule
class MyGlide : AppGlideModule()


class MainPage : AppCompatActivity() {

    private val cameraPermission = android.Manifest.permission.CAMERA
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            startScanner()
        }
    }
    val WineList1: MutableList<WineInfo> = mutableListOf()
    val WineList2: MutableList<WineInfo> = mutableListOf()
    val WineList3: MutableList<WineInfo> = mutableListOf()

//    fun addWine(w:WineInfo) {
//        WineList.add(w)
//    }
//

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val mainPageBinding = MainPageBinding.inflate(layoutInflater)
        val reserveBinding = ReserveBinding.inflate(layoutInflater)
        val reserveView = reserveBinding.root

        val btnIdNumber = mainPageBinding.btn11.id
        var isInfo = true
        var isWineSelected = false
        lateinit var wineTemp:WineInfo

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

        fun displayWine(){

            val floor1wines:JSONArray = floor1.getJSONArray("cell_ids")
            val floor2wines:JSONArray = floor2.getJSONArray("cell_ids")
            val floor3wines:JSONArray = floor3.getJSONArray("cell_ids")
            for ((index,i) in firstfloor.withIndex()){

                for (j in 0 until floor1wines.length()){
                    val wine:JSONObject = floor1wines.getJSONObject(j)
                    if (wine.getInt("col") == index+1){
                        val floor1wine = wine.getJSONObject("wine_id")

                        var aromaList:MutableList<AromaInfo> = mutableListOf()
                        var pairingList:MutableList<PairingInfo> = mutableListOf()
                        var wineAromas:JSONArray = JSONArray()
                        try{
                            wineAromas = floor1wine.getJSONArray("aroma")
                            Log.d("aromacheck1",wineAromas.toString())
                            Log.d("aromacheck11",wineAromas.length().toString())
                        } catch (e:Exception){

                        }
                        for (k in 0 until wineAromas.length()) {
                            Log.d("aromacheck12", k.toString())
                            var wineAroma = AromaInfo("","","", mutableListOf())
                            var nameList: MutableList<String> = mutableListOf()
                            var aromaNames =
                                wineAromas.getJSONObject(k).getJSONArray("aroma_names")
                            Log.d("aromacheck5", aromaNames.toString())
                            wineAroma.Aroma_Id = wineAromas.getJSONObject(k).getString("_id")
                            Log.d("aromacheck6", wineAroma.Aroma_Id.toString())
                            wineAroma.Aroma_category =
                                wineAromas.getJSONObject(k).getString("category")
                            Log.d("aromacheck7", wineAroma.Aroma_category.toString())
                            wineAroma.Aroma_image =
                                wineAromas.getJSONObject(k).getString("imgsrc")
                            Log.d("aromacheck8", wineAroma.Aroma_image.toString())
                            Log.d("aromacheck10",aromaNames.toString())
                            Log.d("aromacheck11",aromaNames.length().toString())
                            for (j in 0 until aromaNames.length()) {
                                Log.d("aromacheck9", j.toString())
                                var aromaName: String = aromaNames.getString(j)
                                nameList.add(aromaName)
                            }
                            wineAroma.Aroma_names = nameList
                            aromaList.add(wineAroma)
                        }
                        try {
                            val winePairings = floor1wine.getJSONArray("pairing")
                            var winePairing = PairingInfo("","","", mutableListOf())
                            var nameList: MutableList<String> = mutableListOf()
                            for (k in 0 until winePairings.length()) {


                                var pairingNames =
                                    winePairings.getJSONObject(k).getJSONArray("pairing_names")

                                winePairing.Pairing_Id =
                                    winePairings.getJSONObject(k).getString("_id")
                                winePairing.Pairing_category =
                                    winePairings.getJSONObject(k).getString("category")
                                winePairing.Pairing_image =
                                    winePairings.getJSONObject(k).getString("imgsrc")

                                for (j in 0 until pairingNames.length()) {
                                    var pairingName: String = pairingNames.getString(j)
                                    nameList.add(pairingName)
                                }
                                winePairing.Pairing_names = nameList
                                pairingList.add(winePairing)
                            }
                        }catch (e:Exception){

                        }
                        try {
                            GlideApp.with(this)
                                .load(floor1wine.getString("imgsrc"))
                                .into(i)
                        }catch (e:Exception){
                        }
                        WineList1.add(WineInfo(
                            wine.getInt("col") - 1
                            ,floor1wine.getString("_id")
                            ,floor1wine.getString("eng_name")
                            ,floor1wine.getString("imgsrc")
                            ,floor1wine.getInt("price")
                            ,floor1wine.getInt("sweet")
                            ,floor1wine.getInt("acid")
                            ,floor1wine.getInt("body")
                            ,floor1wine.getInt("tannin")
                            ,aromaList
                            ,floor1wine.getString("alcohol")
                            ,floor1wine.getInt("temp")
                            ,floor1wine.getString("type")
                            ,pairingList
                        ))
                    }
                }
                i.clipToOutline = true
            }
            for ((index,i) in secondfloor.withIndex()){
                for (j in 0 until floor2wines.length()){

                    val wine:JSONObject = floor2wines.getJSONObject(j)
                    if (wine.getInt("col") == index+1){
                        val floor2wine = wine.getJSONObject("wine_id")
                        var aromaList:MutableList<AromaInfo> = mutableListOf()
                        var pairingList:MutableList<PairingInfo> = mutableListOf()

                        try{
                            val wineAromas = floor2wine.getJSONArray("aroma")
                            lateinit var wineAroma: AromaInfo
                            lateinit var nameList: MutableList<String>

                            for (k in 0 until wineAromas.length()) {

                                var aromaNames =
                                    wineAromas.getJSONObject(k).getJSONArray("aroma_names")

                                wineAroma.Aroma_Id = wineAromas.getJSONObject(k).getString("_id")
                                wineAroma.Aroma_category =
                                    wineAromas.getJSONObject(k).getString("category")
                                wineAroma.Aroma_image =
                                    wineAromas.getJSONObject(k).getString("imgsrc")

                                for (j in 0 until aromaNames.length()) {
                                    var aromaName: String = aromaNames.getString(j)
                                    nameList.add(aromaName)
                                }
                                wineAroma.Aroma_names = nameList
                                aromaList.add(wineAroma)
                            }
                        } catch (e:Exception){

                        }
                        try {
                            val winePairings = floor2wine.getJSONArray("pairing")
                            lateinit var winePairing: PairingInfo
                            lateinit var nameList: MutableList<String>
                            for (k in 0 until winePairings.length()) {


                                var pairingNames =
                                    winePairings.getJSONObject(k).getJSONArray("pairing_names")

                                winePairing.Pairing_Id =
                                    winePairings.getJSONObject(k).getString("_id")
                                winePairing.Pairing_category =
                                    winePairings.getJSONObject(k).getString("category")
                                winePairing.Pairing_image =
                                    winePairings.getJSONObject(k).getString("imgsrc")

                                for (j in 0 until pairingNames.length()) {
                                    var pairingName: String = pairingNames.getString(j)
                                    nameList.add(pairingName)
                                }
                                winePairing.Pairing_names = nameList
                                pairingList.add(winePairing)
                            }
                        }catch (e:Exception){

                        }
                        try {
                            GlideApp.with(this)
                                .load(floor2wine.getString("imgsrc"))
                                .into(i)
                        }catch (e:Exception){
                        }
                        WineList2.add(WineInfo(
                            wine.getInt("col") - 1
                            ,floor2wine.getString("_id")
                            ,floor2wine.getString("eng_name")
                            ,floor2wine.getString("imgsrc")
                            ,floor2wine.getInt("price")
                            ,floor2wine.getInt("sweet")
                            ,floor2wine.getInt("acid")
                            ,floor2wine.getInt("body")
                            ,floor2wine.getInt("tannin")
                            ,aromaList
                            ,floor2wine.getString("alcohol")
                            ,floor2wine.getInt("temp")
                            ,floor2wine.getString("type")
                            ,pairingList
                        ))
                    }
                }
                i.clipToOutline = true
            }
            for ((index,i) in thirdfloor.withIndex()){
                for (j in 0 until floor3wines.length()){

                    val wine:JSONObject = floor3wines.getJSONObject(j)
                    if (wine.getInt("col") == index+1){
                        val floor3wine = wine.getJSONObject("wine_id")
                        var aromaList:MutableList<AromaInfo> = mutableListOf()
                        var pairingList:MutableList<PairingInfo> = mutableListOf()
                        try{
                            val wineAromas = floor3wine.getJSONArray("aroma")
                            lateinit var wineAroma: AromaInfo
                            lateinit var nameList: MutableList<String>
                            for (k in 0 until wineAromas.length()) {


                                var aromaNames =
                                    wineAromas.getJSONObject(k).getJSONArray("aroma_names")

                                wineAroma.Aroma_Id = wineAromas.getJSONObject(k).getString("_id")
                                wineAroma.Aroma_category =
                                    wineAromas.getJSONObject(k).getString("category")
                                wineAroma.Aroma_image =
                                    wineAromas.getJSONObject(k).getString("imgsrc")

                                for (j in 0 until aromaNames.length()) {
                                    var aromaName: String = aromaNames.getString(j)
                                    nameList.add(aromaName)
                                }
                                wineAroma.Aroma_names = nameList
                                aromaList.add(wineAroma)
                            }
                        } catch (e:Exception){

                        }
                        try {
                            val winePairings = floor3wine.getJSONArray("pairing")
                            lateinit var winePairing: PairingInfo
                            lateinit var nameList: MutableList<String>
                            for (k in 0 until winePairings.length()) {


                                var pairingNames =
                                    winePairings.getJSONObject(k).getJSONArray("pairing_names")

                                winePairing.Pairing_Id =
                                    winePairings.getJSONObject(k).getString("_id")
                                winePairing.Pairing_category =
                                    winePairings.getJSONObject(k).getString("category")
                                winePairing.Pairing_image =
                                    winePairings.getJSONObject(k).getString("imgsrc")

                                for (j in 0 until pairingNames.length()) {
                                    var pairingName: String = pairingNames.getString(j)
                                    nameList.add(pairingName)
                                }
                                winePairing.Pairing_names = nameList
                                pairingList.add(winePairing)
                            }
                        }catch (e:Exception){

                        }
                        try {
                            GlideApp.with(this)
                                .load(floor3wine.getString("imgsrc"))
                                .into(i)
                        }catch (e:Exception){
                        }
                        WineList3.add(WineInfo(
                            wine.getInt("col") - 1
                            ,floor3wine.getString("_id")
                            ,floor3wine.getString("eng_name")
                            ,floor3wine.getString("imgsrc")
                            ,floor3wine.getInt("price")
                            ,floor3wine.getInt("sweet")
                            ,floor3wine.getInt("acid")
                            ,floor3wine.getInt("body")
                            ,floor3wine.getInt("tannin")
                            ,aromaList
                            ,floor3wine.getString("alcohol")
                            ,floor3wine.getInt("temp")
                            ,floor3wine.getString("type")
                            ,pairingList
                        ))
                    }
                }
                i.clipToOutline = true
            }
        }

//        val url = "http://10.0.2.2:3000/winecellar/status?id=64ae2b0848a3d71c485e2472"
        var url = "http://13.48.52.200:3000/winecellar/status?id=64b4f9a38b4dc227def9b5b1"
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
            requestCameraAndStartScanner()
//            val intent = Intent(this, ScanPage::class.java)
//            startActivity(intent)
        }
        mainPageBinding.settings.setOnClickListener(){
            val intent = Intent(this,SettingPage::class.java)
            startActivity(intent)

        }
        mainPageBinding.mainSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // On 할 때
                isInfo=false
                mainPageBinding.infoMove.setText("Move")
            } else {
                isInfo=true
                mainPageBinding.infoMove.setText("Info")

            }
        }
        mainPageBinding.mainLogo.setOnClickListener(){
            reserveBinding.minuteET.setText("00")
            reserveBinding.hourET.setText("00")
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


        val cellListener = object : View.OnClickListener {
            override fun onClick (v:View?){

                if (!isInfo) {

                    var clickedCellIndex = v?.id.toString().toInt() - btnIdNumber
                    Log.d("cellllclickedcell",(v?.id.toString().toInt()-btnIdNumber).toString())
                    //Log.d("cellllwinelist1",WineList1[0].Wine_Location.toString())
                    var clickedWineIndex = clickedCellIndex % 5//move 상황
                    var spaceVacant = true
                    if (clickedCellIndex<5){
                        for (w in WineList1){
                            if (w.Wine_Location == clickedWineIndex){
                                spaceVacant = false
                                Log.d("cellllspacevacant", spaceVacant.toString())
                                break
                            }
                        }
                    }
                    else if (clickedCellIndex<10){
                        Log.d("cellllspacevacant1", spaceVacant.toString())
                        Log.d("cellllspacevacant2", WineList2.size.toString())
                        for (w in WineList2){

                            if (w.Wine_Location == clickedWineIndex){
                                Log.d("cellllspacevacant3", spaceVacant.toString())
                                spaceVacant = false
                                break
                            }
                        }
                    }
                    else{
                        for (w in WineList3){
                            if (w.Wine_Location == clickedWineIndex){
                                Log.d("cellllspacevacant", spaceVacant.toString())
                                spaceVacant = false
                                break
                            }
                        }
                    }
                    //Log.d("CellListener9",spaceVacant.toString())
                    if (spaceVacant) {
                        //move 에서 빈칸
                        if(isWineSelected){
                            //move에서 빈칸이고 와인 선택됨
                            isWineSelected = false
                            //Log.d("CellListener6",isWineSelected.toString())
                            wineTemp.Wine_Location = clickedWineIndex
                            Log.d("CellListener7",wineTemp.Wine_Location.toString())
                            if (clickedCellIndex < 5) {
//                                    for ((index,w) in WineList1.withIndex()){
//                                        if (w.Wine_location == wineTemp.Wine_location){
//                                            WineList1[index].Wine_location = clickedWineIndex
//                                        }
//                                    }
                                Log.d("CellListener14",WineList1.size.toString())
                                WineList1.add(wineTemp)
                                Log.d("CellListener15",WineList1.size.toString())
                                //Log.d("CellListener8",WineList1[1].Wine_location.toString())

                            }
                            else if (clickedCellIndex < 10) {
//                                    for ((index,w) in WineList2.withIndex()){
//                                        if (w.Wine_location == wineTemp.Wine_location){
//                                            WineList2[index].Wine_location = clickedWineIndex
//                                        }
//                                    }
                                WineList2.add(wineTemp)
                            }
                            else {
//                                    for ((index,w) in WineList3.withIndex()){
//                                        if (w.Wine_location == wineTemp.Wine_location){
//                                            WineList3[index].Wine_location = clickedWineIndex
//                                        }
//                                    }
                                WineList3.add(wineTemp)
                            }
                            displayWine()



                            Log.d("CellListener",WineList1.toString())
                            //Log.d("CellListener",WineList1[1].Wine_location.toString())


                        }
                    }
                    else {
                        //move에서 와인칸
                        if (!isWineSelected){
                            //아직 옮길 와인 선택 안됨

                            isWineSelected = true
                            //Log.d("wineselelcting",isWineSelected.toString())
                            Log.d("CellListener12",clickedCellIndex.toString())
                            if (clickedCellIndex < 5) { // 1층
                                for ((index,w) in WineList1.withIndex()) {
                                    if (w.Wine_Location == clickedWineIndex){
                                        wineTemp = w.clone()
                                        Log.d("CellListener",wineTemp.Wine_Location.toString())
                                        WineList1.removeAt(index)

                                    }
                                }
                                Log.d("CellListener1",wineTemp.Wine_Location.toString())

                            }
                            else if (clickedCellIndex < 10) {
                                Log.d("CellListener12",clickedCellIndex.toString())
                                for ((index,w) in WineList2.withIndex()) {
                                    if (w.Wine_Location == clickedWineIndex){
                                        Log.d("CellListener12",clickedWineIndex.toString())
                                        wineTemp = w.clone()
                                        Log.d("CellListener13",wineTemp.Wine_Location.toString())
                                        WineList2.removeAt(index)
                                        Log.d("CellListener14",wineTemp.Wine_Location.toString())
                                    }
                                }
                            }
                            else {
                                for ((index,w) in WineList3.withIndex()) {
                                    if (w.Wine_Location == clickedWineIndex){
                                        wineTemp = w.clone()
                                        WineList3.removeAt(index)
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    Log.d("check","check")
                }
            }
        }
        mainPageBinding.btn11.setOnClickListener(cellListener)
        mainPageBinding.btn12.setOnClickListener(cellListener)
        mainPageBinding.btn13.setOnClickListener(cellListener)
        mainPageBinding.btn14.setOnClickListener(cellListener)
        mainPageBinding.btn15.setOnClickListener(cellListener)
        mainPageBinding.btn21.setOnClickListener(cellListener)
        mainPageBinding.btn22.setOnClickListener(cellListener)
        mainPageBinding.btn23.setOnClickListener(cellListener)
        mainPageBinding.btn24.setOnClickListener(cellListener)
        mainPageBinding.btn25.setOnClickListener(cellListener)
        mainPageBinding.btn31.setOnClickListener(cellListener)
        mainPageBinding.btn32.setOnClickListener(cellListener)
        mainPageBinding.btn33.setOnClickListener(cellListener)
        mainPageBinding.btn34.setOnClickListener(cellListener)
        mainPageBinding.btn35.setOnClickListener(cellListener)


    }

    private fun requestCameraAndStartScanner() {
        if (isPermissionGranted(cameraPermission)) {
            startScanner()
        } else {
            requestCameraPermission()
        }
    }

    private fun startScanner() {
        startScanner(this) { barcodes ->
            barcodes.forEach { barcode ->
                when (barcode.valueType) {
                    Barcode.TYPE_URL -> {
                        val dialog = ScanPopup(this@MainPage,"URL",barcode.url.toString())
                        dialog.show()
//                        binding.textViewQrType.text = "URL"
//                        binding.textViewQrContent.text = barcode.url.toString()
                    }
                    Barcode.TYPE_CONTACT_INFO -> {
                        val dialog = ScanPopup(this@MainPage,"CONTACT",barcode.contactInfo.toString())
                        dialog.show()
//                        binding.textViewQrType.text = "Contact"
//                        binding.textViewQrContent.text = barcode.contactInfo.toString()
                    }
                    else -> {
                        val dialog = ScanPopup(this@MainPage,"Other",barcode.rawValue.toString())
                        dialog.show()
//                        val mDialogView = LayoutInflater.from(this).inflate(R.layout.scan_popup, null)
//                        val mBuilder = AlertDialog.Builder(this)
//                            .setView(mDialogView)
//                            .setTitle("Result Form")
//
//                        mBuilder.show()
//                        binding.textViewQrType.text = "Other"
//                        binding.textViewQrContent.text = barcode.rawValue.toString()
                    }
                }
            }
        }
    }

    private fun requestCameraPermission() {
        when {
            shouldShowRequestPermissionRationale(cameraPermission) -> {
                cameraPermissionRequest(
                    positive = { openPermissionSetting() }
                )
            }

            else -> {
                requestPermissionLauncher.launch(cameraPermission)
            }
        }
    }
}