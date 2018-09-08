package com.fs.dcc.webserviceskotlin

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.fs.dcc.webserviceskotlin.data.HttpClientWeather
import com.fs.dcc.webserviceskotlin.data.JSONParserWeather
import com.fs.dcc.webserviceskotlin.model.Weather
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat
import java.text.DecimalFormat
import java.util.*

class MainActivity(var weather: Weather) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        renderDataWeather("Merida,MX")

    }

    fun renderDataWeather(city: String) {

        val  weatherTask = WeatherTask()
        weatherTask.execute(*arrayOf(city + "&APPID="+ "816dd52631303d7b52e87838ce48d2e9" + "&units=metrics"))

    }

    private inner class WeatherTask: AsyncTask<String, Void, Weather>() {

        override fun doInBackground(vararg p0: String?): Weather {

            val data = HttpClientWeather().getWeatherData(p0[0])
            weather = JSONParserWeather.getWeather(data)!!

            return weather

        }

        override fun onPostExecute(result: Weather?) {
            super.onPostExecute(result)

            val formatDate = DateFormat.getTimeInstance()
            val sunrise = formatDate.format(Date(weather.place!!.sunRise))
            val sunset = formatDate.format(Date(weather.place!!.sunSet))
            val update = formatDate.format(Date(weather.place!!.lastUpdate))

            val formatDecimal = DecimalFormat("#.#")
            val tempFormat = formatDecimal.format(weather.actualCondition.temperature)

            textViewCity.text = weather.place!!.city + "," + weather.place!!.country
            textViewTemp.text = "" + tempFormat + "C"
            textViewWet.text = "Humedad: " + weather.actualCondition.humidity
            textViewPressure.text = "Presión: " + weather.actualCondition.rainFall
            textViewWind.text = "Viento: " + weather.wind.speed + "mps"
            textViewSunrise.text = "Amanecer: " + sunrise
            textViewSunset.text = "Puesta de sol: " + sunset
            textViewLastUpdate.text = "Última actulización es: " + update
            textViewCloud.text = "Nube: " + weather.actualCondition.condition + "(" + weather.actualCondition.description +")"

        }

    }

}
