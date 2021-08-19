package com.ivanov.newsapi.presentation.fragments.map.util

import android.content.Context
import android.location.LocationManager

object MapUtil {
    fun isGeolocationEnabled(context: Context): Boolean {
        return (context.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
            .isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}