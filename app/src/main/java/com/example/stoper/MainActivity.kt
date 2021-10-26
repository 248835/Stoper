package com.example.stoper

import android.app.Activity
import android.content.Intent
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
    private lateinit var recyclerView: RecyclerView

    private val viewModel: MainViewModel by viewModels()

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

        time = findViewById(R.id.chronometer)
        millisecondsTextView = findViewById(R.id.milliseconds)

        adapter = MainAdapter()
        recyclerView = findViewById(R.id.tag_list)
        recyclerView.adapter = adapter

        tagButton = findViewById<Button>(R.id.tag).apply {
            setOnClickListener {
                val currentTime = SystemClock.elapsedRealtime() - time.base
                viewModel.tags.add(Tag(
                    viewModel.tags.size + 1L,
                    currentTime,
                    viewModel.tags.lastOrNull()?.let { currentTime - it.measuredTime } ?: 0
                ))
                adapter.submitList(viewModel.tags.toList())
                recyclerView.smoothScrollToPosition(viewModel.tags.size)
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
                viewModel.tags.clear()
                adapter.submitList(listOf())
            }
        }
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

data class Tag(val id: Long, val measuredTime: Long, val elapsedTime: Long)

@Parcelize
data class Colors(
    @ColorInt val textColor: Int,
    @ColorInt val backgroundColor: Int,
    @ColorInt val actionBarColor: Int
) : Parcelable