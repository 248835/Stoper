package com.example.stoper

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Parcelable
import android.os.SystemClock
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
import kotlinx.android.parcel.Parcelize

class MainActivity : AppCompatActivity() {
    private lateinit var time: Chronometer
    private lateinit var millisecondsTextView: TextView
    private lateinit var restartButton: Button
    private lateinit var tagButton: Button
    private lateinit var layout: ConstraintLayout
    private lateinit var adapter: MainAdapter

    private val viewModel: MainViewModel by viewModels()

    private val openPostActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.getParcelableExtra<Colors>(COLORS_PARCEL)?.let {
                    supportActionBar?.setBackgroundDrawable(ColorDrawable(it.actionBarColor))
                    window.statusBarColor = it.actionBarColor
                    layout.background = ColorDrawable(it.backgroundColor)
                    adapter.fontColor = it.fontColor
                    time.setTextColor(it.fontColor)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layout = findViewById(R.id.layout)

        time = findViewById(R.id.chronometer)
        millisecondsTextView = findViewById(R.id.milliseconds)

        tagButton = findViewById<Button>(R.id.tag).apply {
            setOnClickListener {
                val currentTime = SystemClock.elapsedRealtime() - time.base
                viewModel.addTag(
                    Tag(
                        viewModel.tags.value!!.size + 1L,
                        currentTime,
                        viewModel.tags.value?.lastOrNull()?.let { currentTime - it.timeTaken } ?: 0
                    )
                )
            }
        }
        findViewById<Button>(R.id.start).apply {
            setOnClickListener {
                if (!viewModel.isTimerRunning) {
                    startTime()
                } else {
                    stopTime()
                }
            }
        }

        restartButton = findViewById<Button>(R.id.restart).apply {
            setOnClickListener {
                time.base = SystemClock.elapsedRealtime()
                time.stop()
                viewModel.isTimerRunning = false
                viewModel.lastPause = null
                viewModel.clearTags()
            }
        }

        adapter = MainAdapter()
        findViewById<RecyclerView>(R.id.tag_list).adapter = adapter
        viewModel.tags.observe(this, { it ->
            it?.let {
                adapter.data = it
            }
        })
    }

    private fun startTime() {
        time.base = viewModel.lastPause?.let {
            time.base + SystemClock.elapsedRealtime() - it
        } ?: SystemClock.elapsedRealtime()
        time.start()
        time.setOnChronometerTickListener {
        }
        viewModel.isTimerRunning = true
        tagButton.isEnabled = true
        restartButton.isEnabled = false
    }

    private fun stopTime() {
        viewModel.lastPause = SystemClock.elapsedRealtime()
        time.stop()
        time.onChronometerTickListener = null
        viewModel.isTimerRunning = false
        tagButton.isEnabled = false
        restartButton.isEnabled = true
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

data class Tag(val id: Long, val timeTaken: Long, val timeBetween: Long)

@Parcelize
data class Colors(
    @ColorInt val fontColor: Int = Color.RED,
    @ColorInt val backgroundColor: Int = Color.BLUE,
    @ColorInt val actionBarColor: Int = Color.RED
) : Parcelable