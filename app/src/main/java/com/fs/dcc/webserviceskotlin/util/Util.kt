package com.fs.dcc.webserviceskotlin.util

import org.json.JSONException
import org.json.JSONObject

/**
 * Created by danielcintoconde on 03/09/18.
 */
object Util {

    val BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q="
    val ICON_URL = "http://api.openweathermap.org/img/w/"

    @Throws(JSONException::class)
    fun getObject(tagName: String, jsonObject: JSONObject): JSONObject {
        return jsonObject.getJSONObject(tagName)
    }

    @Throws(JSONException::class)
    fun getString(tagName: String, jsonObject: JSONObject): String {
        return jsonObject.getString(tagName)
    }

    @Throws(JSONException::class)
    fun getFloat(tagName: String, jsonObject: JSONObject): Float {
        return jsonObject.getDouble(tagName).toFloat()
    }

    @Throws(JSONException::class)
    fun getDouble(tagName: String, jsonObject: JSONObject): Double {
        return jsonObject.getDouble(tagName)
    }

    @Throws(JSONException::class)
    fun getInt(tagName: String, jsonObject: JSONObject): Int {
        return jsonObject.getInt(tagName)
    }

    @Throws(JSONException::class)
    fun getLong(tagName: String, jsonObject: JSONObject): Long {
        return jsonObject.getLong(tagName)
    }

}