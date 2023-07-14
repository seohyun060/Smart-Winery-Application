package com.example.smart_winery

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull

class ScanPopup(@NonNull context: Context, private val barcodeTypeValue: String ,private val barcodeValue: String) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scan_popup)

        val proceedButton = findViewById<Button>(R.id.proceed)
        val cancelButton = findViewById<Button>(R.id.cancel)
        val barcodeType = findViewById<TextView>(R.id.text_view_qr_type)
        barcodeType.text = barcodeTypeValue
        val barcodeContent = findViewById<TextView>(R.id.text_view_qr_content)
        barcodeContent.text = barcodeValue

        proceedButton.setOnClickListener {
            Toast.makeText(context, "확인", Toast.LENGTH_SHORT).show()
            dismiss()
        }

        cancelButton.setOnClickListener {
            Toast.makeText(context, "취소", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }
}
