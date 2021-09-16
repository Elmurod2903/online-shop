package com.example.onlineshop.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.onlineshop.databinding.ActivityGmailBinding
import com.example.onlineshop.utils.PrefUtils

class GmailActivity : AppCompatActivity() {
    lateinit var binding: ActivityGmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val newGmail = binding.etGmail.text.toString()
            validateEnailAddress(newGmail)

        }
        binding.cardBack.setOnClickListener { finish() }
    }

    private fun validateEnailAddress(newGmail: String) {
        if (!newGmail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(newGmail).matches() ){
            Toast.makeText(this,"New email validated successfully",Toast.LENGTH_SHORT).show()
            PrefUtils.setNewGmail(newGmail)
            finish()
            startActivity(Intent(this, MainActivity::class.java))

        }else{
            Toast.makeText(this,"Invalidated email address",Toast.LENGTH_SHORT).show()
        }
    }

}
