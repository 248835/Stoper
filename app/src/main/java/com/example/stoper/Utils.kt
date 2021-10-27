package com.example.stoper

import android.content.res.Resources
import com.example.stoper.stopper.convertLongToTime

fun convertMeasurementIdToFormatted(id: Long, res: Resources): String {
    return res.getString(R.string.measurment, id)
}

fun convertElapsedTimeToFormatted(elapsedTime: Long, res: Resources): String {
    return res.getString(R.string.elapsed, elapsedTime.convertLongToTime())
}

fun convertMeasuredTimeToFormatted(measuredTime: Long, res: Resources): String {
    return res.getString(R.string.measuredTime, measuredTime.convertLongToTime())
}