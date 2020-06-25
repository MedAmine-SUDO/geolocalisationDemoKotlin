package esprims.gi2.tp4nejiamine

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = getFusedLocationProviderClient(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Define places
        val sydney = LatLng(-34.0, 151.0)
        val monastir = LatLng(35.76, 10.81)
        val sousse = LatLng(35.82, 10.64)
        val tunis = LatLng(36.8, 10.17)

        val cameraPosition = CameraPosition.Builder()
            .target(tunis)
            .zoom(10F)
            .bearing(45F)
            .tilt(90F)
            .build()

        //Uncomment these lines to run them
        //Add marker for different places
        //mMap.addMarker(MarkerOptions().position(tunis).title("Tunis"))
        //mMap.addMarker(MarkerOptions().position(sousse).title("Marker in Sousse"))
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(tunis, 10F))

        //Draw line between monastir and sousse
        //mMap.addPolyline(PolylineOptions().add(monastir, sousse))

        //Draw
        //mMap.addCircle(
        //    CircleOptions().radius(3000.0).center(tunis).fillColor(Color.BLUE)
        //        .strokeColor(Color.RED)
        //)
        //mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        mMap.setOnMapClickListener {
            Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
        }

        mMap.setOnMarkerClickListener {
            var openUrl = Intent(Intent.ACTION_VIEW)
            openUrl.data = Uri.parse("https://fr.wikipedia.org/wiki/" + it.title)
            startActivity(openUrl)
            true
        }

        getLastPosition()
    }

    private fun getLastPosition() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(
                applicationContext,
                "Vous devez activer l'autorisation",
                Toast.LENGTH_LONG
            ).show()
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                2
            )

        } else {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    Toast.makeText(
                        this,
                        "Your last location coordinates \nLatitude: " + it.latitude + " Logitude: " + it.longitude + ";",
                        Toast.LENGTH_LONG
                    ).show()
                    //Add marker on your last position
                    mMap.addMarker(
                        MarkerOptions().position(LatLng(it.latitude, it.longitude))
                            .title("Last position")
                    )
                    // Move the camera to the position with zoom
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 10F))

                    //Implement Geocoder
                    var geocoder = Geocoder(applicationContext, Locale.getDefault())
                    var address = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                    Toast.makeText(this, address.get(0).getAddressLine(0).toString(), Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this, "Unknown last location", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode==1){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                getLastPosition()
            else Toast.makeText(applicationContext, "Accès non autorisé !", Toast.LENGTH_LONG).show()
        }
    }
}