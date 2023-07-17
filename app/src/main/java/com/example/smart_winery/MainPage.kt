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
import android.view.View
import android.view.ViewGroup
import android.widget.Space
import androidx.appcompat.app.AlertDialog
import com.example.smart_winery.databinding.MainPageBinding
import com.example.smart_winery.databinding.ReserveBinding


@GlideModule
class MyGlide : AppGlideModule()


class MainPage : AppCompatActivity() {

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
        val url = "http://13.48.52.200:3000/winecellar/winename?id=64ae2b9048a3d71c485e2476"
        WineList1.add(WineInfo(0,getDrawable(R.drawable.wine1)))
        WineList1.add(WineInfo(3,getDrawable(R.drawable.wine2)))
        WineList2.add(WineInfo(2,getDrawable(R.drawable.wine3)))
        WineList2.add(WineInfo(0,getDrawable(R.drawable.wine1)))
        WineList3.add(WineInfo(2,getDrawable(R.drawable.wine2)))
        WineList3.add(WineInfo(4,getDrawable(R.drawable.wine3)))
        lateinit var wineTemp:WineInfo
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
        fun displayWine(){
            for ((index,i) in firstfloor.withIndex()){
                GlideApp.with(this)
                    .load("")
                    .into(i)
                i.clipToOutline = true
                for (w in WineList1){
                    //Log.d("displaylog",w.Wine_location.toString())
                    //Log.d("displaylog",index.toString())
                    if (w.Wine_location == index){
                        i.background = w.Wine_Img
                        break
                    }
                    else {
                        i.background = getDrawable(R.drawable.red_border)
                    }
                }
            }
            for ((index,i) in secondfloor.withIndex()){
                GlideApp.with(this)
                    .load("")
                    .into(i)
                i.clipToOutline = true
                for (w in WineList2){
                    if (w.Wine_location == index){
                        i.background = w.Wine_Img
                        break
                    }
                    else {
                        i.background = getDrawable(R.drawable.white_border)
                    }
                }
            }
            for ((index,i) in thirdfloor.withIndex()){
                GlideApp.with(this)
                    .load("")
                    .into(i)
                i.clipToOutline = true
                for (w in WineList3){
                    if (w.Wine_location == index){
                        i.background = w.Wine_Img
                        break
                    }
                    else {
                        i.background = getDrawable(R.drawable.sparkling_border)
                    }
                }
            }
        }
        displayWine()

        setContentView(mainPageBinding.root)
        mainPageBinding.addWine.setOnClickListener() {
            val intent = Intent(this, ScanPage::class.java)
            startActivity(intent)
        }
        mainPageBinding.settings.setOnClickListener(){
            val intent = Intent(this,SettingPage::class.java)
            startActivity(intent)

        }

        mainPageBinding.mainSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // On 할 때
                isInfo=false
                mainPageBinding.infoMove.setText("Move     ")
            } else {
                isInfo=true
                mainPageBinding.infoMove.setText("Info     ")

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
                    var clickedWineIndex = clickedCellIndex % 5//move 상황
                    var spaceVacant = true
                    if (clickedCellIndex<5){
                        for (w in WineList1){
                            if (w.Wine_location == clickedWineIndex){
                                spaceVacant = false
                                break
                            }
                        }
                    }
                    else if (clickedCellIndex<10){
                        for (w in WineList2){
                            if (w.Wine_location == clickedWineIndex){
                                spaceVacant = false
                                break
                            }
                        }
                    }
                    else{
                        for (w in WineList3){
                            if (w.Wine_location == clickedWineIndex){
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
                                wineTemp.Wine_location = clickedWineIndex

                                //Log.d("CellListener7",wineTemp.Wine_location.toString())

                                if (clickedCellIndex < 5) {
                                    WineList1.add(wineTemp)
                                    //Log.d("CellListener8",WineList1[1].Wine_location.toString())

                                }
                                else if (clickedCellIndex < 10) {
                                    WineList2.add(wineTemp)
                                }
                                else {
                                    WineList3.add(wineTemp)
                                }

                                displayWine()

                                //Log.d("CellListener",WineList1[0].Wine_location.toString())
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
                                    if (w.Wine_location == clickedWineIndex){

                                        wineTemp = w
                                        Log.d("CellListener",WineList1[index].Wine_location.toString())
                                        WineList1.removeAt(index)

                                    }
                                }
                                //Log.d("CellListener1",wineTemp.Wine_location.toString())

                            }
                            else if (clickedCellIndex < 10) {
                                Log.d("CellListener12",clickedCellIndex.toString())
                                for ((index,w) in WineList2.withIndex()) {
                                    if (w.Wine_location == clickedWineIndex){
                                        Log.d("CellListener12",clickedWineIndex.toString())
                                        wineTemp = w
                                        Log.d("CellListener13",wineTemp.Wine_location.toString())
                                        WineList2.removeAt(index)
                                        Log.d("CellListener14",wineTemp.Wine_location.toString())
                                    }
                                }
                            }
                            else {
                                for ((index,w) in WineList3.withIndex()) {
                                    if (w.Wine_location == clickedWineIndex){
                                        wineTemp = w
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
}