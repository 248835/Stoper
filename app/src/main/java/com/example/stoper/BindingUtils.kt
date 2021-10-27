package com.example.stoper

import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import com.example.stoper.stopper.Tag

@BindingAdapter("measurementIdFormatted")
fun TextView.setMeasurementIdFormatted(item: Tag?) {
    item?.let {
        text = convertMeasurementIdToFormatted(it.id, context.resources)
    }
}

@BindingAdapter("elapsedTimeFormatted")
fun TextView.setElapsedTimeFormatted(item: Tag?) {
    item?.let {
        text = convertElapsedTimeToFormatted(it.elapsedTime, context.resources)
    }
}

@BindingAdapter("measuredTimeFormatted")
fun TextView.setMeasuredTimeFormatted(item: Tag?) {
    item?.let {
        text = convertMeasuredTimeToFormatted(it.measuredTime, context.resources)
    }
}

@BindingAdapter("textColor")
fun TextView.setTextColor(@ColorInt colorInt: Int?){
    setTextColor(colorInt ?: currentTextColor)
}