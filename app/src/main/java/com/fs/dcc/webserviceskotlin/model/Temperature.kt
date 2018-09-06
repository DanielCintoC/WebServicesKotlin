package com.fs.dcc.webserviceskotlin.model

/**
 * Created by danielcintoconde on 05/09/18.
 */
data class Temperature(var temperature: Double = 0.toDouble(), var minTemperature: Double = 0.toDouble(),
                       var maxTemperature: Double = 0.toDouble())