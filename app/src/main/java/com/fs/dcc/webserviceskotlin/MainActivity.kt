package com.fs.dcc.webserviceskotlin

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import com.fs.dcc.webserviceskotlin.data.HttpClientWeather
import com.fs.dcc.webserviceskotlin.data.JSONParserWeather
import com.fs.dcc.webserviceskotlin.data.PreferencesCity
import com.fs.dcc.webserviceskotlin.model.Weather
import com.fs.dcc.webserviceskotlin.util.Util
import kotlinx.android.synthetic.main.activity_main.*
import org.apache.http.HttpStatus
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import java.io.IOException
import java.io.InputStream
import java.text.DateFormat
import java.text.DecimalFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    var weather = Weather()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cityPreference: PreferencesCity = PreferencesCity(this)

        renderDataWeather(cityPreference.city)

    }

    fun renderDataWeather(city: String) {

        val  weatherTask = WeatherTask()
        weatherTask.execute(*arrayOf(city + "&APPID="+ "816dd52631303d7b52e87838ce48d2e9" + "&units=metrics"))

    }

    fun showDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Cambiar Ciudad")

        val putCity = EditText(this)
        putCity.inputType = InputType.TYPE_CLASS_TEXT
        putCity.hint = "Merida,MX"
        builder.setView(putCity)
        builder.setPositiveButton("OK") {dialogInterface, i ->

            val cityPrefs = PreferencesCity(this)
            cityPrefs.city = putCity.text.toString()
            val newCity = cityPrefs.city
            renderDataWeather(newCity)
        }.show()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId){
            R.id.menu_change -> {
                showDialog()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("StaticFieldLeak")
    private inner class WeatherTask: AsyncTask<String, Void, Weather>() {

        override fun doInBackground(vararg p0: String?): Weather {

            val data = HttpClientWeather().getWeatherData(p0[0])
            weather = JSONParserWeather.getWeather(data)!!
            weather.dataIcon = weather.actualCondition.icon
            DownloadImageAsync().execute(weather.dataIcon)

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

    @SuppressLint("StaticFieldLeak")
    private inner class DownloadImageAsync: AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg p0: String?): Bitmap {
            return downloadImage(p0[0] as String)
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            imageViewIcon.setImageBitmap(result)
        }

        fun downloadImage(code: String): Bitmap {

            val client = DefaultHttpClient()
            val getRequest = HttpGet(Util.ICON_URL + code + ".png")

            try {

                val response  = client.execute(getRequest)
                val statusCode = response.statusLine.statusCode

                if (statusCode != HttpStatus.SC_OK){
                    Log.w("Error descarga imagen: ", statusCode.toString())
                    return null!!
                }

                val entity = response.entity
                if (entity != null){
                    val inputStream: InputStream? = entity.content

                    return BitmapFactory.decodeStream(inputStream)
                }

            }catch (e: IOException){
                e.printStackTrace()
            }
            return null!!
        }

    }

}
