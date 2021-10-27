package com.example.stoper.stopper

import android.os.SystemClock
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stoper.tag.Tag
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StopperViewModel : ViewModel() {
    private var _isRunning = false
    val isRunning: Boolean
        get() = _isRunning
    private var lastPause = 0L
    private var startTime = 0L
    private val _tags: MutableList<Tag> = mutableListOf()
    val tags: List<Tag>
        get() = _tags

    fun start(setText: (String) -> Unit) {
        startTime = SystemClock.elapsedRealtime()
        _isRunning = true
        viewModelScope.launch {
            while (_isRunning) {
                delay(30)
                setText.invoke((lastPause + SystemClock.elapsedRealtime() - startTime).convertLongToTime())
            }
            lastPause += SystemClock.elapsedRealtime() - startTime
        }
    }

    fun stop() {
        _isRunning = false
    }

    fun restart() {
        lastPause = 0
        _tags.clear()
    }

    fun addTag(){
        val currentTime = lastPause + SystemClock.elapsedRealtime() - startTime
        _tags.add(
            Tag(
                _tags.size + 1L,
                currentTime,
                _tags.lastOrNull()?.let { currentTime - it.measuredTime } ?: 0
            )
        )
    }
}