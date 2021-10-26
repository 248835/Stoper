package com.example.stoper

import android.content.res.Resources
import java.text.SimpleDateFormat
import java.util.*

fun convertMeasurementIdToFormatted(id: Long, res: Resources): String {
    return res.getString(R.string.measurment, id)
}

fun convertElapsedTimeToFormatted(elapsedTime: Long, res: Resources): String {
    return res.getString(R.string.elapsed, elapsedTime.convertLongToTime(), elapsedTime % 1000)
}

fun convertMeasuredTimeToFormatted(measuredTime: Long, res: Resources): String {
    return res.getString(
        R.string.measuredTime,
        measuredTime.convertLongToTime(),
        measuredTime % 1000
    )
}

private fun Long.convertLongToTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat("mm:ss", Locale.getDefault())
    return format.format(date)
}