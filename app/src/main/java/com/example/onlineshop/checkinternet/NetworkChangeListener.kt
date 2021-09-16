package com.example.onlineshop.checkinternet

import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent

import android.widget.Toast

import java.lang.NullPointerException

class NetworkChangeListener : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        try {
            if (Common.isConnectToInternet(context)) {
                Toast.makeText(context, "No internet connected", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(context, "Internet connected", Toast.LENGTH_SHORT).show()

            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

    }
}