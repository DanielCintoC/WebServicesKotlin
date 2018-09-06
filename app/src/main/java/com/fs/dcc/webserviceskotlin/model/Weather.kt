package com.fs.dcc.webserviceskotlin.model

/**
 * Created by danielcintoconde on 05/09/18.
 */
data class Weather(var place: Place? = null, var dataIcon: String? = null,
                   var actualCondition: ActualCondition = ActualCondition(),
                   var temperature: Temperature = Temperature(),
                   var wind: Wind = Wind(),
                   var snow: Snow = Snow(),
                   var clouds: Clouds = Clouds())