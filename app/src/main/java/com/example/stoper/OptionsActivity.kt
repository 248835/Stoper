package com.example.stoper

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.annotation.IdRes
import com.example.stoper.MainActivity.Companion.COLORS_PARCEL
import com.google.android.material.slider.Slider

class OptionsActivity : AppCompatActivity() {

    private val actionBarColor = RGBColors()
    private val backgroundColor = RGBColors()
    private val fontColor = RGBColors()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        findViewById<Button>(R.id.accept).setOnClickListener {
            val data = Intent()
            data.putExtra(
                COLORS_PARCEL,
                Colors(fontColor.getColor(), backgroundColor.getColor(), actionBarColor.getColor())
            )

            setResult(Activity.RESULT_OK, data)
            finish()
        }

        findViewById<Button>(R.id.cancel).setOnClickListener { finish() }

        handleColorPickers(
            actionBarColor,
            R.id.actionBarColorPickerRed,
            R.id.actionBarColorPickerGreen,
            R.id.actionBarColorPickerBlue
        )

        handleColorPickers(
            backgroundColor,
            R.id.backgroundColorPickerRed,
            R.id.backgroundColorPickerGreen,
            R.id.backgroundColorPickerBlue
        )

        handleColorPickers(
            fontColor,
            R.id.fontColorPickerRed,
            R.id.fontColorPickerGreen,
            R.id.fontColorPickerBlue
        )
    }

    private fun handleColorPickers(
        rgbColors: RGBColors,
        @IdRes redPicker: Int,
        @IdRes greenPicker: Int,
        @IdRes bluePicker: Int
    ) {
        findViewById<Slider>(redPicker).addOnChangeListener { _, value, _ ->
            rgbColors.red = value.toInt()
        }
        findViewById<Slider>(greenPicker).addOnChangeListener { _, value, _ ->
            rgbColors.green = value.toInt()
        }
        findViewById<Slider>(bluePicker).addOnChangeListener { _, value, _ ->
            rgbColors.blue = value.toInt()
        }
    }
}

data class RGBColors(var red: Int = 0, var green: Int = 0, var blue: Int = 0) {
    fun getColor() = Color.rgb(red, green, blue)
}