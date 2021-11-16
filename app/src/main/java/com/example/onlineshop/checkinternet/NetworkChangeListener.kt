package com.example.onlineshop.checkinternet

import android.app.Activity
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi

import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import com.example.onlineshop.R
import com.example.onlineshop.view.MainActivity

import java.lang.NullPointerException

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class NetworkChangeListener(private val activity: Activity, context: Context) :
    BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        try {
            if (Common.isConnectToInternet(context)) {
                // false
                alertDialog.show()

            } else {
                // true
                alertDialog?.dismiss()

            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

    }

    private val alertDialog by lazy(LazyThreadSafetyMode.NONE) {

        AlertDialog.Builder(context)
            .setCancelable(false)
            .setView(R.layout.no_internet_dialog)
            .setPositiveButton(
                activity.getString(R.string.yes)
            ) { dialog, which ->
                startActivityForResult(
                    activity,
                    Intent(Settings.ACTION_WIFI_SETTINGS),
                    0, null
                )
            }
            .setNegativeButton(
                activity.getString(R.string.no)
            ) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }

}