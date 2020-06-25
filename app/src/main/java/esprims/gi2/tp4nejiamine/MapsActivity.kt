package esprims.gi2.tp4nejiamine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        val monastir = LatLng(35.76, 10.81)
        val sousse = LatLng(35.82, 10.64)
        val tunis = LatLng(36.8, 10.17)

        val cameraPosition = CameraPosition.Builder()
            .target(monastir)
            .zoom(10F)
            .bearing(45F)
            .tilt(90F)
            .build()
        mMap.addMarker(MarkerOptions().position(monastir).title("Marker in Monastir"))
        mMap.addMarker(MarkerOptions().position(sousse).title("Marker in Sousse"))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sousse, 10F))
        mMap.mapType=GoogleMap.MAP_TYPE_SATELLITE
        mMap.addPolyline(PolylineOptions().add(monastir, sousse))
        mMap.addCircle(CircleOptions())
        //mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

    }
}