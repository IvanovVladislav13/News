package com.ivanov.newsapi.presentation.fragments.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.ivanov.newsapi.R
import com.ivanov.newsapi.presentation.fragments.map.util.MapUtil

class MapFragment : Fragment(R.layout.map_fragment), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener {

    private var permissionDenied = false
    private lateinit var map: GoogleMap

    private val requestMultiplePermissions =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
                && permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                enableMyLocation()
            } else {
                permissionDenied = true
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap ?: return
        map.setOnMyLocationButtonClickListener(this)
        enableMyLocation()
    }

    private fun enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
        } else {
            requestMultiplePermissions.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        if (MapUtil.isGeolocationEnabled(requireContext())) {
            Toast.makeText(
                requireContext(),
                getString(R.string.location_button_click_text),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.gps_disabled_text),
                Toast.LENGTH_LONG
            ).show()
        }

        return false
    }

    override fun onResume() {
        super.onResume()

        if (permissionDenied) {
            showMissingPermissionError()
            permissionDenied = false
        }
    }

    private fun showMissingPermissionError() {
        Toast.makeText(
            requireContext(),
            getString(R.string.permission_denied_text),
            Toast.LENGTH_LONG
        ).show()
    }
}