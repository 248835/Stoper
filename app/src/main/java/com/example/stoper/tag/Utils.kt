package com.example.stoper.tag

import android.content.res.Resources
import com.example.stoper.R
import com.example.stoper.stopper.convertLongToTime

fun convertMeasurementIdToFormatted(id: Long, res: Resources): String {
    return res.getString(R.string.measurement, id)
}

fun convertElapsedTimeToFormatted(elapsedTime: Long, res: Resources): String {
    return res.getString(R.string.elapsed, elapsedTime.convertLongToTime())
}

fun convertMeasuredTimeToFormatted(measuredTime: Long, res: Resources): String {
    return res.getString(R.string.measuredTime, measuredTime.convertLongToTime())
}

data class Tag(val id: Long, val measuredTime: Long, val elapsedTime: Long)