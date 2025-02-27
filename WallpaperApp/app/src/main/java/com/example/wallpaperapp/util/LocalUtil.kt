package com.example.wallpaperapp.util

import android.content.Context
import android.content.res.Configuration
import java.util.Locale


fun setLocale(
    context: Context,
    languageCode: String,
) {   // Locale is responsible for app language which is changed dynamically at run time
    val locale = Locale(languageCode)    // creating locale object,  Locale IS A Class
    Locale.setDefault(locale)   // updating default Locale with newly created locale

    val config =
        Configuration(context.resources.configuration)  // creting a copy of a Configuration object, which holds various settings about app display ( scale, orientation,screen size, and locale)
    config.setLocale(locale)  // updating a configurations locale with a new locale which was created above
    // without this app won't load resources in the desired language

    context.resources.updateConfiguration(
        config,
        context.resources.displayMetrics
    )  // without this line UI would not reflect language change immediately
}
