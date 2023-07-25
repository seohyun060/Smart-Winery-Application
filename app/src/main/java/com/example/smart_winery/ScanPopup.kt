package com.example.smart_winery

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.smart_winery.databinding.ScanPopupBinding
import org.json.JSONArray
import org.json.JSONObject

class ScanPopup(@NonNull context: Context, private val barcodeTypeValue: String ,private val barcodeValue: String) : Dialog(context) {

    private lateinit var binding: ScanPopupBinding
    var flag: Int = -1
    var type: String = ""
    var input_row: Int = -1
    var input_col: Int = -1
    var count=0
    lateinit var moveWineArray: JSONArray
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ScanPopupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        var count=0

        var url = "http://13.48.52.200:3000/"
        var get_url = "winecellar/cell?cellarid=64b4f9a38b4dc227def9b5b1&wineid="
        var post_url = "winecellar/cell"
        get_url += barcodeValue
        val queue : RequestQueue = Volley.newRequestQueue(this.context)
        var get_data:JSONObject = JSONObject()
        var post_data:JSONObject = JSONObject()
        val get_request = JsonObjectRequest(Request.Method.GET, url + get_url, null, { response ->
            Log.d("get_request","RESPONSE = $response")
            post_data = response
            Log.d("post_request", "POST DATA = $post_data")
            flag = response.getInt("flag")
            if(flag==4){
                binding.scanType.setText("Not Wine")
                binding.scanType.setTextColor(Color.parseColor("#000000"))
                binding.scanImage.setImageResource(R.drawable.invalid)
                binding.proceed.setTextColor(ContextCompat.getColor(this.context, R.color.proceed))
                binding.proceed.isEnabled=true
                binding.resultText.text = "Invalid Barcode"
                binding.moreInfoBtn.visibility= GONE
            }
            else {
                type = response.getString("input_wine_type")
                binding.scanType.setText(type)

                when (type) {
                    "Red" ->
                        binding.scanType.setTextColor(Color.parseColor("#9B1132"))

                    "White" ->
                        binding.scanType.setTextColor(Color.parseColor("#CFE449"))

                    "Sparkling" ->
                        binding.scanType.setTextColor(Color.parseColor("#2589FF"))

                    else ->
                        binding.scanType.setTextColor(Color.parseColor("#000000"))
                }
                input_row = response.getInt("input_row")
                input_col = response.getInt("input_col")
                binding.wineName.setText(response.getString("input_name"))
                Glide.with(this.context)
                    .load(response.getString("imgsrc").toUri()) // 불러올 이미지 url
                    .into(binding.scanImage) // 이미지를 넣을 뷰
                when (flag) {
                    0 -> {
                        binding.proceed.setTextColor(
                            ContextCompat.getColor(
                                this.context,
                                R.color.proceed
                            )
                        )
                        binding.proceed.isEnabled = true
                        binding.resultText.text = "Cannot Add Wine"
                        binding.moreInfoBtn.isEnabled = false
                        binding.moreInfoBtn.visibility = GONE
                    }

                    1 -> {
                        binding.proceed.setTextColor(
                            ContextCompat.getColor(
                                this.context,
                                R.color.proceed
                            )
                        )
                        binding.proceed.isEnabled = true
                        when(input_row){
                            1 -> binding.resultText.text = "Add to 1st Layer?"
                            2 -> binding.resultText.text = "Add to 2nd Layer?"
                            3 -> binding.resultText.text = "Add to 3rd Layer?"
                        }
                        binding.moreInfoBtn.isEnabled = false
                        binding.moreInfoBtn.visibility = GONE
                    }

                    2 -> {
                        binding.proceed.setTextColor(
                            ContextCompat.getColor(
                                this.context,
                                R.color.grey_button
                            )
                        )
                        binding.proceed.isEnabled = false
                        binding.resultText.text = "Can added be to ($input_row, $input_col)"
                        binding.moreInfoBtn.isEnabled = true
                        binding.moreInfoBtn.visibility = VISIBLE
                        moveWineArray = response.getJSONArray("move_wine")
                    }
                }
            }
        }, { error ->
            Log.e("TAG_get", "RESPONSE IS $error")
            // in this case we are simply displaying a toast message.
            Toast.makeText(this.context, "Fail to get response", Toast.LENGTH_SHORT)
                .show()
        })
        queue.add(get_request)
//        val req1 = """
//            {
//                "cellarid": "64b4f9a38b4dc227def9b5b1",
//                "input_wine_id": $barcodeValue,
//                "input_row": 2,
//                "input_col": 1,
//                "floor1[type]": 2,
//                "floor1[temp_target]": 9,
//                "floor1[is_smart_mode]": true,
//                "floor2[type]": 1,
//                "floor2[temp_target]": 13,
//                "floor2[is_smart_mode]": true,
//                "floor3[type]": 1,
//                "floor3[temp_target]": 7,
//                "floor3[is_smart_mode]": true,
//                "move_wine[]": []
//            }
//        """.trimIndent()
//        var req:JSONObject = JSONObject(req1)

        binding.proceed.setOnClickListener {
            if (flag==0 || flag==4) {
                dismiss()
            }
            else {
                Log.d("flag_num", "FLAG_IN_PROCEED_ELSE = $flag")
                Log.d("post_request", "POST DATA BEFORE = $post_data")
                val post_request = JsonObjectRequest(Request.Method.POST, url + post_url, post_data, { response ->
                    dismiss()
                    MainPage.instance?.finish()
                    val intents = Intent(this.context,MainPage::class.java)
                    context.startActivity(intents)
                    }, { error ->
                        Log.e("TAG_post", "RESPONSE IS $error")
                        // in this case we are simply displaying a toast message.
                        Toast.makeText(this.context, "Fail to get response", Toast.LENGTH_SHORT)
                            .show()
                    })
                queue.add(post_request)
            }
        }
        binding.cancel.setOnClickListener {
            Toast.makeText(this.context, "취소", Toast.LENGTH_SHORT)
                .show()
            dismiss()
        }

        binding.moreInfoBtn.setOnClickListener {
            binding.topText.text="How To Add"
            binding.scanType.visibility= GONE
            binding.moreInfoBtn.isEnabled=true
            binding.moreInfoBtn.visibility= VISIBLE
            binding.moreInfoBtn.text="Next"

            val moveWineObject = moveWineArray.getJSONObject(count)
            val curRow = moveWineObject.getInt("cur_row")
            val curCol = moveWineObject.getInt("cur_col")
            val nextRow = moveWineObject.getInt("next_row")
            val nextCol = moveWineObject.getInt("next_col")
            val imgSrc = moveWineObject.getString("img_src")
            val wineName = moveWineObject.getString("wine_name")
            Log.d("img_src","$imgSrc")
            count+=1

            Glide.with(this.context)
                .load(imgSrc.toUri()) // 불러올 이미지 url
                .into(binding.scanImage)

            binding.wineName.text=wineName
            binding.resultText.text="Move ($curRow, $curCol) to ($nextRow, $nextCol)"

            if (count == moveWineArray.length()){
                binding.proceed.setTextColor(ContextCompat.getColor(this.context, R.color.proceed))
                binding.proceed.isEnabled=true
                binding.moreInfoBtn.visibility = GONE
            }
        }
    }
}
