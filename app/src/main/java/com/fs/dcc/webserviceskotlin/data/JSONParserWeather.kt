package com.fs.dcc.webserviceskotlin.data

import com.fs.dcc.webserviceskotlin.model.Place
import com.fs.dcc.webserviceskotlin.model.Weather
import com.fs.dcc.webserviceskotlin.util.Util
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by danielcintoconde on 08/09/18.
 */
object JSONParserWeather {

    val weather = Weather()

    fun getWeather(data: String): Weather? {

        try {

            val jsonObject = JSONObject(data)

            val place = Place()
            val coordObject = Util.getObject("coord", jsonObject)
            place.lat = Util.getFloat("lat", coordObject)
            place.lon = Util.getFloat("lon", coordObject)

            val sysObject = Util.getObject("sys", jsonObject)
            place.country = Util.getString("country", sysObject)
            place.lastUpdate = Util.getLong("dt", jsonObject)
            place.sunRise = Util.getLong("sunrise", sysObject)
            place.sunSet = Util.getLong("sunset", sysObject)
            place.city = Util.getString("name", jsonObject)
            weather.place = place

            val mainObject = Util.getObject("main", jsonObject)
            weather.actualCondition.humidity = Util.getFloat("humidity", mainObject)
            weather.actualCondition.temperature = Util.getDouble("temp", mainObject)
            weather.actualCondition.rainFall = Util.getFloat("pressure", mainObject)
            weather.actualCondition.maxTemperature = Util.getFloat("temp_max", mainObject)
            weather.actualCondition.minTemperature = Util.getFloat("temp_min", mainObject)

            val jsonArray = jsonObject.getJSONArray("weather")
            val jsonWeather = jsonArray.getJSONObject(0)
            weather.actualCondition.weatherId = Util.getInt("id", jsonWeather)
            weather.actualCondition.description = Util.getString("description", jsonWeather)
            weather.actualCondition.condition = Util.getString("main", jsonWeather)
            weather.actualCondition.icon = Util.getString("icon", jsonWeather)

            val windObject = Util.getObject("wind", jsonObject)
            weather.wind.speed = Util.getFloat("speed", windObject)
            weather.wind.centigrade = Util.getFloat("deg", windObject)

            val cloudObject = Util.getObject("clouds", jsonObject)
            weather.clouds.rainFall = Util.getInt("all", cloudObject)


            return weather


        } catch (e: JSONException) {
            e.printStackTrace()
            return null
        }

    }

}