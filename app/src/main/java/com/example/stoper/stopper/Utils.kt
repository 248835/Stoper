package com.example.stoper.stopper

import java.util.concurrent.TimeUnit

fun Long.convertLongToTime(): String {
    val seconds = TimeUnit.SECONDS.convert(this, TimeUnit.MILLISECONDS)
    val minutes = TimeUnit.MINUTES.convert(this, TimeUnit.MILLISECONDS)
    val hours = TimeUnit.HOURS.convert(this, TimeUnit.MILLISECONDS)
    return "%02d:%02d:%02d.%03d".format(hours,minutes%60,seconds%60,this%1000)
}

data class Tag(val id: Long, val measuredTime: Long, val elapsedTime: Long)