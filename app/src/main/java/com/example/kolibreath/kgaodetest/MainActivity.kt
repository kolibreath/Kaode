package com.example.kolibreath.kgaodetest

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView
import com.amap.api.maps.model.MyLocationStyle
import com.example.kolibreath.kgaodetest.extensions.find
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {

    private val mMapView: MapView by find(R.id.main_mv)
    private var aMap : AMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mMapView.onCreate(savedInstanceState)

        //初始化Amap
        if(aMap == null)
            aMap = mMapView.map

        initBluePoint()
        basicLocation()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        mMapView.onSaveInstanceState(outState)
    }


    fun initBluePoint(){

        var locationStyle = MyLocationStyle().apply {
            interval(2000)
            this.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW)
        }
        aMap = aMap.apply {
            this?.myLocationStyle = locationStyle
            this?.isMyLocationEnabled = true
        }
    }

    fun basicLocation(){
        val locationOption = AMapLocationClientOption().apply {
            setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy)
            setInterval(2000)
        }

        val locationClient = AMapLocationClient(this).apply {
            setLocationOption(locationOption)
            setLocationListener {
               Log.d("fuck","locationType:${it.locationType} latitude:${it.latitude}")
            }
        }
        locationClient.startLocation()

    }

    fun requestPermission(){

    }
}

