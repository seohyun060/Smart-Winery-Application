package com.example.smart_winery

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import com.example.smart_winery.databinding.ScanPopupBinding
class ScanPopup(@NonNull context: Context, private val barcodeTypeValue: String ,private val barcodeValue: String) : Dialog(context) {

    private lateinit var binding: ScanPopupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ScanPopupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.textViewQrContent.text = barcodeValue

        binding.proceed.setOnClickListener {
            dismiss()
        }
        binding.cancel.setOnClickListener {
            Toast.makeText(context, "취소!", Toast.LENGTH_SHORT).show()
            dismiss()
        }

    }
}
