package com.example.stoper.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
            Log.e("STEFAN","$it")
            it.textColor?.let {
                findViewById<ImageView>(R.id.textColor).setBackgroundColor(it)
            }
            it.actionBarColor?.let {
                findViewById<ImageView>(R.id.appBarColor).setBackgroundColor(it)
            }
            it.backgroundColor?.let {
                findViewById<ImageView>(R.id.backgroundColor).setBackgroundColor(it)
            }
        }

        findViewById<Button>(R.id.textButton).setOnClickListener {
            val colorPickerDialog = ColorPickerDialog.createColorPickerDialog(this)
            colorPickerDialog.show()
            colorPickerDialog.setOnColorPickedListener { color, _ ->
                findViewById<ImageView>(R.id.textColor).setBackgroundColor(color)
                textColor=color
            }
        }

        findViewById<Button>(R.id.appBarButton).setOnClickListener {
            val colorPickerDialog = ColorPickerDialog.createColorPickerDialog(this)
            colorPickerDialog.show()
            colorPickerDialog.setOnColorPickedListener { color, _ ->
                findViewById<ImageView>(R.id.appBarColor).setBackgroundColor(color)
                actionBarColor=color
            }
        }

        findViewById<Button>(R.id.backgroundButton).setOnClickListener {
            val colorPickerDialog = ColorPickerDialog.createColorPickerDialog(this)
            colorPickerDialog.show()
            colorPickerDialog.setOnColorPickedListener { color, _ ->
                findViewById<ImageView>(R.id.backgroundColor).setBackgroundColor(color)
                backgroundColor=color
            }
        }

        findViewById<Button>(R.id.accept).setOnClickListener {
            val data = Intent()
            data.putExtra(
                COLORS_PARCEL,
                Colors(textColor, backgroundColor, actionBarColor)
            )

            setResult(Activity.RESULT_OK, data)
            finish()
        }

        findViewById<Button>(R.id.cancel).setOnClickListener { finish() }
    }
}