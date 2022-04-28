package com.bangkit.intermediate.dicodingstoryapp.ui.story.map

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bangkit.intermediate.dicodingstoryapp.R
import com.bangkit.intermediate.dicodingstoryapp.data.remote.response.Story
import com.bangkit.intermediate.dicodingstoryapp.databinding.ActivityStoryLocationBinding
import com.bangkit.intermediate.dicodingstoryapp.ui.story.detail.StoryDetailActivity

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class StoryLocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityStoryLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStoryLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        val story = intent.getParcelableExtra<Story>(StoryDetailActivity.EXTRA_STORY) as Story
        Log.e(TAG, "DIS STORY $story")

        // Add a marker in Sydney and move the camera
        val location = LatLng(story.latitude, story.longitude)
        mMap.addMarker(MarkerOptions().position(location).title(story.name).snippet(story.description))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))

        setMapStyle()
    }

    private fun setMapStyle() {
        try {
            val success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (success) return
            Log.e(TAG, "Style parsing failed")
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: $exception")
        }
    }

    companion object {
        private const val TAG = "StoryLocationActivity"
    }
}