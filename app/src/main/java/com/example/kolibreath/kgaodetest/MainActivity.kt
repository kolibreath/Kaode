package com.example.kolibreath.kgaodetest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.amap.api.maps.MapView
import com.example.kolibreath.kgaodetest.extensions.find

class MainActivity : AppCompatActivity() {

   private val mMapView: MapView by find(R.id.main_mv)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mMapView.onCreate(savedInstanceState)
    }

}
