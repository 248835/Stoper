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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        intent.extras?.getParcelable<Colors>(COLORS_PARCEL)?.let {
            it.textColor?.let {
                findViewById<ImageView>(R.id.text_color).setBackgroundColor(it)
            }
            it.actionBarColor?.let {
                findViewById<ImageView>(R.id.appbar_color).setBackgroundColor(it)
            }
            it.backgroundColor?.let {
                findViewById<ImageView>(R.id.background_color).setBackgroundColor(it)
            }
        }

        findViewById<Button>(R.id.text_button).setOnClickListener {
            val colorPickerDialog = ColorPickerDialog.createColorPickerDialog(this)
            colorPickerDialog.show()
            colorPickerDialog.setOnColorPickedListener { color, _ ->
                findViewById<ImageView>(R.id.text_color).setBackgroundColor(color)
                textColor=color
            }
        }

        findViewById<Button>(R.id.appbar_button).setOnClickListener {
            val colorPickerDialog = ColorPickerDialog.createColorPickerDialog(this)
            colorPickerDialog.show()
            colorPickerDialog.setOnColorPickedListener { color, _ ->
                findViewById<ImageView>(R.id.appbar_color).setBackgroundColor(color)
                actionBarColor=color
            }
        }

        findViewById<Button>(R.id.background_button).setOnClickListener {
            val colorPickerDialog = ColorPickerDialog.createColorPickerDialog(this)
            colorPickerDialog.show()
            colorPickerDialog.setOnColorPickedListener { color, _ ->
                findViewById<ImageView>(R.id.background_color).setBackgroundColor(color)
                backgroundColor=color
            }
        }

        findViewById<Button>(R.id.accept_button).setOnClickListener {
            val data = Intent()
            data.putExtra(
                COLORS_PARCEL,
                Colors(textColor, backgroundColor, actionBarColor)
            )

            setResult(Activity.RESULT_OK, data)
            finish()
        }

        findViewById<Button>(R.id.cancel_button).setOnClickListener { finish() }
    }
}