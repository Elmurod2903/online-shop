package com.example.onlineshop.view

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.onlineshop.checkinternet.NetworkChangeListener
import com.example.onlineshop.databinding.ActivityGmailBinding
import com.example.onlineshop.utils.PrefUtils

class GmailActivity : AppCompatActivity() {
    lateinit var binding: ActivityGmailBinding
    private var broadcastReceiver: BroadcastReceiver? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        broadcastReceiver = NetworkChangeListener(this, this)


        binding.btnSave.setOnClickListener {
            val newGmail = binding.etGmail.text.toString()
            validateEmailAddress(newGmail)

        }
        binding.cardBack.setOnClickListener { finish() }
    }

    private fun validateEmailAddress(newGmail: String) {
        if (!newGmail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(newGmail).matches()) {
            Toast.makeText(this, "New email validated successfully", Toast.LENGTH_SHORT).show()
            PrefUtils.setNewGmail(newGmail)
            finish()

        } else {
            Toast.makeText(this, "Invalidated email address", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onStop() {
        unregisterReceiver(broadcastReceiver)
        super.onStop()
    }


}
