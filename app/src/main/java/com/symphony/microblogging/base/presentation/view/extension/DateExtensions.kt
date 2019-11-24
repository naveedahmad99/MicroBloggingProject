package com.symphony.microblogging.base.presentation.view.extension

import java.text.SimpleDateFormat
import java.util.*

fun Date.convertDateToString(date: Date): String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.forLanguageTag("en"))
    return simpleDateFormat.format(date)
}