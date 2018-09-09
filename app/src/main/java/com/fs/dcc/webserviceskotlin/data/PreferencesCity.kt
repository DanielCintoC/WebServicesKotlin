package com.fs.dcc.webserviceskotlin.data

import android.app.Activity
import android.content.SharedPreferences

/**
 * Created by danielcintoconde on 09/09/18.
 */
class PreferencesCity(activity: Activity) {

    var prefs: SharedPreferences

    init {
        prefs = activity.getPreferences(Activity.MODE_PRIVATE)
    }

    var city: String
    get() = prefs.getString("city", "Puebla,MX")
    set(city) = prefs.edit().putString("city", city).apply()
}