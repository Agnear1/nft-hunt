package com.example.nfthunt/*
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(),OnMapReadyCallback {






    //-----Android functions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    // Got last known location. In some rare situations this can be null.
                    Toast.makeText(this, location.toString(), Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "pipi", Toast.LENGTH_SHORT).show()
                }


        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    //TODO udpate location on UI
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        setContentView(R.layout.start)
    }

    override fun onResume() {
        super.onResume()
        //setContentView(R.layout.activity_main)
        Toast.makeText(this, "Adnrés sale pute", Toast.LENGTH_SHORT)
                .show()
        if (checkPermission()) {
            Toast.makeText(this, "Vincent sale pute", Toast.LENGTH_SHORT)
                    .show()
            //startLocationUpdates()
        }

        val myBtn: Button = findViewById(R.id.locateButton)
        getLastLocation()

        myBtn.setOnClickListener {
            Toast.makeText(this, "userLoc", Toast.LENGTH_LONG).show()
        }


    }

    //----My functions---------

    private fun checkPermission():Boolean{
        return ActivityCompat.
        checkSelfPermission(
                this, android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        Toast.makeText(this, "requestPermission", Toast.LENGTH_SHORT).show()
        ActivityCompat.requestPermissions(
                this,
                arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PERMISSION_ID

        )

    }

    private fun isLocationEnabled():Boolean{
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }


    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Debug ", "You have the permission")
            }
        }
    }


    //TODO: make it more readable
    private fun getLastLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if(checkPermission()){
            if(isLocationEnabled()){
                //TODO correct this
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    val location = task.result
                    if (location != null) {
                        val myTxt: TextView = findViewById(R.id.printLocation)
                        val currentLoc = "Latitude : " + location.latitude + "\nLongitude : " + location.longitude
                        myTxt.text=currentLoc

                    }else{
                        Toast.makeText(this, "Location is null...fetching",Toast.LENGTH_SHORT).show()

                    }
                }
            }else{
                Toast.makeText(this, "Enable location services", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Give location permission to the app", Toast.LENGTH_SHORT).show()
        }
    }


/*
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProvider.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    } */


    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        val sydney = LatLng(0.0 , 0.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Mon marker"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

    }


    fun nftFetcher() {

        var x: Array<NFT>


        data class nftItem(
                val id: String = "UniqueId",
                val itemLocation: Location
                //image
        )

    }
}

private class NFT {
    //TODO build this method
}
*/
