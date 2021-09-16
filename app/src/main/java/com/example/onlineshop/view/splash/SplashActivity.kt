package com.example.onlineshop.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager

import com.example.onlineshop.databinding.ActivitySplashBinding
import com.example.onlineshop.view.MainActivity
import com.example.onlineshop.view.login.LoginActivity
import com.example.onlineshop.utils.PrefUtils

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        )
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.animationView.postDelayed({
            finish()
            if (PrefUtils.getToken().isNullOrEmpty()) {
                startActivity(Intent(this, LoginActivity::class.java))

            } else {
                startActivity(Intent(this, MainActivity::class.java))

            }
        }, 3000)

    }
}