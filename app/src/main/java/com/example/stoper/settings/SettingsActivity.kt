package com.example.stoper.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.azeesoft.lib.colorpicker.ColorPickerDialog
import com.example.stoper.MainActivity.Companion.COLORS_PARCEL
import com.example.stoper.R

class OptionsActivity : AppCompatActivity() {

    private var actionBarColor: Int? = null
    private var backgroundColor: Int? = null
    private var textColor: Int? = null

    private lateinit var appbarColorImageView: ImageView
    private lateinit var textColorImageView: ImageView
    private lateinit var backgroundColorImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        appbarColorImageView = findViewById(R.id.appbar_color)
        textColorImageView = findViewById(R.id.text_color)
        backgroundColorImageView = findViewById(R.id.background_color)

        intent.extras?.getParcelable<Colors>(COLORS_PARCEL)?.let {
            it.textColor?.let {
                textColorImageView.setBackgroundColor(it)
            }
            it.actionBarColor?.let {
                appbarColorImageView.setBackgroundColor(it)
            }
            it.backgroundColor?.let {
                backgroundColorImageView.setBackgroundColor(it)
            }
        }

        setClickListeners()
    }

    private fun setClickListeners() {
        findViewById<Button>(R.id.text_button).setOnClickListener {
            textColorPicker()
        }

        textColorImageView.setOnClickListener {
            textColorPicker()
        }

        findViewById<Button>(R.id.appbar_button).setOnClickListener {
            appbarColorPicker()
        }

        appbarColorImageView.setOnClickListener {
            appbarColorPicker()
        }

        findViewById<Button>(R.id.background_button).setOnClickListener {
            backgroundColorPicker()
        }

        backgroundColorImageView.setOnClickListener {
            backgroundColorPicker()
        }

        findViewById<Button>(R.id.accept_button).setOnClickListener {
            val data = Intent()
            data.putExtra(
                COLORS_PARCEL,
                Colors(textColor, backgroundColor, actionBarColor)
            )

            setResult(RESULT_OK, data)
            finish()
        }

        findViewById<Button>(R.id.cancel_button).setOnClickListener { finish() }
    }

    private fun backgroundColorPicker() {
        val colorPickerDialog = ColorPickerDialog.createColorPickerDialog(this)
        colorPickerDialog.show()
        colorPickerDialog.setOnColorPickedListener { color, _ ->
            findViewById<ImageView>(R.id.background_color).setBackgroundColor(color)
            backgroundColor = color
        }
    }

    private fun appbarColorPicker() {
        val colorPickerDialog = ColorPickerDialog.createColorPickerDialog(this)
        colorPickerDialog.show()
        colorPickerDialog.setOnColorPickedListener { color, _ ->
            findViewById<ImageView>(R.id.appbar_color).setBackgroundColor(color)
            actionBarColor = color
        }
    }

    private fun textColorPicker() {
        val colorPickerDialog = ColorPickerDialog.createColorPickerDialog(this)
        colorPickerDialog.show()
        colorPickerDialog.setOnColorPickedListener { color, _ ->
            findViewById<ImageView>(R.id.text_color).setBackgroundColor(color)
            textColor = color
        }
    }
}