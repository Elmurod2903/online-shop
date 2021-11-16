package com.example.onlineshop

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.onlineshop.checkinternet.NetworkChangeListener

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.onlineshop.databinding.ActivityMapsBinding
import com.example.onlineshop.model.AddressModel
import org.greenrobot.eventbus.EventBus

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var broadcastReceiver: BroadcastReceiver? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        broadcastReceiver = NetworkChangeListener(this, this)


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.btnConfirmAddress.setOnClickListener {
            val addressModel = AddressModel(
                "",
                mMap.cameraPosition.target.latitude,
                mMap.cameraPosition.target.longitude
            )
            EventBus.getDefault().post(addressModel)
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

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