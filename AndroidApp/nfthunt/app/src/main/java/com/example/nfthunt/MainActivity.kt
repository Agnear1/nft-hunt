package com.example.nfthunt

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity: AppCompatActivity(), OnMapReadyCallback {


    private val FINE_LOCATION_RQ = 101
    private val CAMERA_RQ = 102


    private lateinit var shoesImg : ImageView
    private lateinit var cityImg : ImageView



    var monNFT1 = NFTS(
            id = "000_000_000",
            nftName = "shoes",
            nftLatitude = 50.50F,
            nftLongitude = 50.50F,
            illustration = findViewById<ImageView>(shoesImg)
    )

    var monNFT2 = NFTS(
            id = "000_000_001",
            nftName = "city",
            nftLatitude = 51.1F,
            nftLongitude = 51.1F,
            illustration = R.drawable.city
    )


    private lateinit var mMap:  GoogleMap
    private lateinit var supportMapFragment: SupportMapFragment
    private lateinit var cameraUpdate: CameraUpdate

    private lateinit var mUserLoc : LatLng

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback : LocationCallback
    private var requestingLocationUpdates : Boolean = false

    private lateinit var mapCenterButton: Button


    override fun onMapReady(googleMap: GoogleMap) {

            Log.d("map", googleMap.toString())
            mMap = googleMap
            Log.d("map", mMap.toString())

    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment.getMapAsync(this)


        checkForPermissions(ACCESS_FINE_LOCATION,
                "LOCATION",
                FINE_LOCATION_RQ)

        //Updating location loading
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    Log.d("locResult", locationResult.toString())
                    mUserLoc = LatLng(location.latitude, location.longitude)
                }
            }
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
        createLocationRequest()
        mapCenterButton = findViewById(R.id.mapCenterButton)

    }


    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        requestingLocationUpdates = false
    }

    override fun onResume() {
        super.onResume()
        requestingLocationUpdates = true
        if (requestingLocationUpdates) createLocationRequest()


        mapCenterButton.setOnClickListener {
            //on affiche un marker
            Log.d("centering", "can reach")
            getLastLocation()
            mMap.addMarker(MarkerOptions()
                    .position(mUserLoc)
                    .title("currentLoc")
            )
            cameraZooming()
            Toast.makeText(this, mUserLoc.toString(), Toast.LENGTH_SHORT).show()
            Log.d("centering", mUserLoc.toString())
            nftMarker()
        }
    }

    private fun checkForPermissions(permission: String, name: String, requestCode: Int ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(applicationContext, permission)
                        == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(applicationContext, "$name permission granted", Toast.LENGTH_LONG)
                            .show()
                }
                shouldShowRequestPermissionRationale(permission) -> showDialog(permission, name, requestCode)

                else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }
        }
    }

    private fun showDialog(permission: String, name: String, requestCode: Int) {
        //Dialog
        val builder = AlertDialog.Builder(this)
        builder.apply{
            setMessage("Permission to access location is required")
            setTitle("location perm")
            setPositiveButton("OK") { dialog, which ->
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
            }
            val dialog = builder.create()
            dialog.show()
        }
    }


    @SuppressLint("MissingPermission")
    private fun getLastLocation()   {

        fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    if  (location != null) {
                        Log.d("validLoc", "lat : ${location.latitude} lon : ${location.longitude}")
                        Log.d("currentLoc", location.toString())

                    } else {
                        createLocationRequest()
                    }
                }
    }

    private fun createLocationRequest() {

        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        Log.d("creatingLocationRequest", locationRequest.toString() )

        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return
        }

        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper()
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        fun innerCheck(name: String) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "$name permission refused", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "$name permission granted", Toast.LENGTH_LONG).show()
            }
        }

        when (requestCode) {
            FINE_LOCATION_RQ -> innerCheck("location")
            CAMERA_RQ -> innerCheck("camera")
        }
    }

    //set the camera basics
    //refers to https://developers.google.com/maps/documentation/android-sdk/views
    private fun cameraZooming() {
        mMap.isBuildingsEnabled = false
        cameraUpdate = CameraUpdateFactory.newLatLngZoom(mUserLoc, 10.0f)

        mMap.setMinZoomPreference(6.0f)
        mMap.setMaxZoomPreference(14.0f)
        mMap.animateCamera(cameraUpdate)

    }

    private fun nftMarker() {


    shoesImg = getDrawable(monNFT1.illustration)

        val shoesPos = LatLng(monNFT1.nftLatitude.toDouble(), monNFT2.nftLongitude.toDouble())
        var mark1 = mMap.addMarker(MarkerOptions()
                .position(shoesPos)
                .title("City")
                .anchor(0.5f, 0.0f)
        )

        val cityPos = LatLng(monNFT2.nftLatitude.toDouble(), monNFT2.nftLongitude.toDouble())
        var mark2 = mMap.addMarker(MarkerOptions()
                .position(cityPos)
                .title("City")
                .anchor(0.5f, 0.0f)
                .icon(BitmapDrawable(shoesImg))
                )
    }
}





