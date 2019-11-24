package com.symphony.microblogging.util

import android.content.res.Resources
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

fun formatDate(date: Date = Calendar.getInstance().time, format: String = "YYYY-MM-dd"): String {
	val df = SimpleDateFormat(format, Locale.getDefault())
	return df.format(date)
}

inline fun <reified T> Gson.fromJson(json: String) =
		this.fromJson<T>(json, object : TypeToken<T>() {}.type)

fun Int.dpToPx(): Float {
	return this * Resources.getSystem().displayMetrics.density
}
