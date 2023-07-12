package com.example.smart_winery

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smart_winery.databinding.LoginPageBinding


class LoginPage : AppCompatActivity() {
    @SuppressLint("UseCompatLoadingForDrawables")

    override fun onCreate(savedInstanceState: Bundle?) {
        val loginPageBinding = LoginPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(loginPageBinding.root)
       loginPageBinding.loginNaver.setOnClickListener {
           val intent = Intent(this, MainPage::class.java)
           startActivity(intent)
       }
        loginPageBinding.loginKakao.setOnClickListener {
            val intent = Intent(this, MainPage::class.java)
            startActivity(intent)
        }
    }
}