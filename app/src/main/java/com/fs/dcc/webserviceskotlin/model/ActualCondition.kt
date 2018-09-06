package com.fs.dcc.webserviceskotlin.model

/**
 * Created by danielcintoconde on 05/09/18.
 */
data class ActualCondition(var weatherId: Int = 0, var condition:String? = null,
                           var description: String? = null, var icon: String? = null,
                           var rainFall: Float = 0.toFloat(), var humidity: Float = 0.toFloat(),
                           var maxTemperature: Float = 0.toFloat(), var minTemperature: Float = 0.toFloat(),
                           var temperature: Double = 0.toDouble())