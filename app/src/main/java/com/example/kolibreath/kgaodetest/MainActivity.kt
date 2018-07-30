package com.example.kolibreath.kgaodetest

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView
import com.amap.api.maps.model.MyLocationStyle
import com.example.kolibreath.kgaodetest.extensions.find
import java.util.logging.Logger
import android.content.Intent




class MainActivity : AppCompatActivity() {

    private val mMapView: MapView by find(R.id.main_mv)
    private var aMap : AMap? = null
    private val REQUEST_CODE = 1000
    private val mBtn : Button by find(R.id.main_btn)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermission()

        mMapView.onCreate(savedInstanceState)

        //初始化Amap
        if(aMap == null)
            aMap = mMapView.map

        initBluePoint()
        basicLocation()

        mBtn.setOnClickListener(){
            startActivity(Intent(this.applicationContext,
                    com.amap.api.maps.offlinemap.OfflineMapActivity::class.java))
        }
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
        var permissions =  arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE)

        for(permission in permissions){
            if(ContextCompat.checkSelfPermission(this,permission)!=PackageManager.PERMISSION_GRANTED){
               if(ActivityCompat.shouldShowRequestPermissionRationale(this,permission)){

               }else{
                   ActivityCompat.requestPermissions(this, arrayOf(permission),REQUEST_CODE)
               }
            }
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            REQUEST_CODE ->{
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.d("fuck","user granted")
                }
            }else ->{
                  Log.d("fuck","user denied!")
            }
        }
    }
}

