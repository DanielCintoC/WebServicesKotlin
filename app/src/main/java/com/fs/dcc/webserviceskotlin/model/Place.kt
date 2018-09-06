package com.fs.dcc.webserviceskotlin.model

/**
 * Created by danielcintoconde on 05/09/18.
 */
data class Place(var lon: Float = 0.toFloat(), var lat: Float = 0.toFloat(),
                 var sunRise: Long = 0, var sunSet: Long = 0,
                 var country: String? = null, var city: String? = null,
                 var lastUpdate: Long = 0)