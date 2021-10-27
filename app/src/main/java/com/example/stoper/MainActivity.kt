package com.example.stoper

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Parcelable
import android.os.SystemClock
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.stoper.stopper.Stopper
import kotlinx.android.parcel.Parcelize

class MainActivity : AppCompatActivity() {
    private lateinit var time: Chronometer
    private lateinit var layout: ConstraintLayout
    private lateinit var adapter: MainAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var stopper: Stopper
    private lateinit var tagButton: Button
    private lateinit var restartButton: Button

    private val openPostActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.getParcelableExtra<Colors>(COLORS_PARCEL)?.let {
                    supportActionBar?.setBackgroundDrawable(ColorDrawable(it.actionBarColor))
                    window.statusBarColor = it.actionBarColor
                    layout.background = ColorDrawable(it.backgroundColor)
                    adapter.textColor = it.textColor
                    time.setTextColor(it.textColor)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layout = findViewById(R.id.layout)
        stopper = findViewById(R.id.stopper)

        adapter = MainAdapter()
        recyclerView = findViewById(R.id.tag_list)
        recyclerView.adapter = adapter

        tagButton = findViewById<Button>(R.id.tag).apply {
            setOnClickListener {
                stopper.addTag()
                adapter.submitList(stopper.tags.toList())
                recyclerView.smoothScrollToPosition(stopper.tags.size)
            }
        }

        restartButton = findViewById<Button>(R.id.restart).apply {
            setOnClickListener {
                adapter.submitList(listOf())
                stopper.restart()
            }
        }

        findViewById<Button>(R.id.start).apply {
            setOnClickListener {
                if (stopper.isRunning()) {
                    stopper.stop()
                    tagButton.isEnabled = false
                    restartButton.isEnabled = true
                } else {
                    stopper.start()
                    tagButton.isEnabled = true
                    restartButton.isEnabled = false
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.options) {
            openPostActivity.launch(
                Intent(this, OptionsActivity::class.java)
            )
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val COLORS_PARCEL = "COLORS_PARCEL"
    }
}

@Parcelize
data class Colors(
    @ColorInt val textColor: Int,
    @ColorInt val backgroundColor: Int,
    @ColorInt val actionBarColor: Int
) : Parcelable