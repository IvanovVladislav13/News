package com.ivanov.newsapi.presentation.fragments.map

import android.app.Application
import android.content.Context
import android.location.LocationManager
import androidx.lifecycle.AndroidViewModel

class MapViewModel(application: Application) : AndroidViewModel(application) {
    fun isGeolocationEnabled(context: Context): Boolean {
        return (context.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
            .isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}