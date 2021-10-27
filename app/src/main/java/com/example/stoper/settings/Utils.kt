package com.example.stoper.settings

import android.os.Parcelable
import androidx.annotation.ColorInt
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Colors(
    @ColorInt val textColor: Int?,
    @ColorInt val backgroundColor: Int?,
    @ColorInt val actionBarColor: Int?
) : Parcelable