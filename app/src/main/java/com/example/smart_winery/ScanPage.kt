package com.example.smart_winery

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smart_winery.databinding.ScanPageBinding
import android.graphics.Bitmap
import android.provider.MediaStore
import android.Manifest

class ScanPage : AppCompatActivity() {
    @SuppressLint("UseCompatLoadingForDrawables")

    val CAMERA = arrayOf(Manifest.permission.CAMERA)
    val wineId = -1
    val STORAGE = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val CAMERA_CODE = 98
    val STORAGE_CODE = 99
    override fun onCreate(savedInstanceState: Bundle?) {
        val scanPageBinding = ScanPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(scanPageBinding.root)
       scanPageBinding.scanButton.setOnClickListener() {
            ScanBarcode()

       }
//        scanPageBinding.picture.setOnClickListener(){
//            GetAlbum()
//        }
    }
    fun ScanBarcode(){
            val itt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(itt, CAMERA_CODE)
    }

}