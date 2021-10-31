package com.example.stoper

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.stoper.settings.Colors
import com.example.stoper.settings.OptionsActivity
import com.example.stoper.stopper.Stopper
import com.example.stoper.tag.MainAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var layout: ConstraintLayout
    private lateinit var adapter: MainAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var stopper: Stopper
    private lateinit var tagButton: Button
    private lateinit var restartButton: Button
    private var supportActionBarColor: Int? = null
    private var backgroundColor: Int? = null

    private val openPostActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.getParcelableExtra<Colors>(COLORS_PARCEL)?.let {
                    it.actionBarColor?.let {
                        supportActionBar?.setBackgroundDrawable(ColorDrawable(it))
                        supportActionBarColor = it
                        window.statusBarColor = it
                    }
                    it.backgroundColor?.let {
                        layout.background = ColorDrawable(it)
                        backgroundColor = it
                    }
                    it.textColor?.let {
                        adapter.textColor = it
                        stopper.setTextColor(it)
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBarColor = baseContext.getColor(R.color.design_default_color_primary)
        backgroundColor = baseContext.getColor(R.color.design_default_color_background)

        layout = findViewById(R.id.layout)
        stopper = findViewById(R.id.stopper)

        adapter = MainAdapter()
        recyclerView = findViewById(R.id.tag_list)
        recyclerView.adapter = adapter

        tagButton = findViewById<Button>(R.id.tag).apply {
            setOnClickListener {
                stopper.addTag()
            }
        }

        stopper.tags.observe(this, {
            adapter.submitList(it)
            recyclerView.smoothScrollToPosition(it?.size ?: 0)
        })

        restartButton = findViewById<Button>(R.id.restart_button).apply {
            setOnClickListener {
                adapter.submitList(listOf())
                stopper.restart()
            }
        }

        findViewById<Button>(R.id.start_button).apply {
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
            val intent = Intent(this, OptionsActivity::class.java)
            intent.putExtra(
                COLORS_PARCEL,
                Colors(stopper.currentTextColor, backgroundColor, supportActionBarColor)
            )
            openPostActivity.launch(
                intent
            )
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val COLORS_PARCEL = "COLORS_PARCEL"
    }
}