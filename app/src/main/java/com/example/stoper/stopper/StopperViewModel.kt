package com.example.stoper.stopper

import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stoper.tag.Tag
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class StopperViewModel : ViewModel() {
    private var _isRunning = false
    val isRunning: Boolean
        get() = _isRunning
    private var lastPause = 0L
    private var startTime = 0L
    private val _tags: MutableLiveData<List<Tag>> = MutableLiveData(listOf())
    val tags: LiveData<List<Tag>>
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
        _tags.value = listOf()
    }

    fun addTag() {
        val currentTime = lastPause + SystemClock.elapsedRealtime() - startTime
        _tags.value = arrayOf(
            *_tags.value!!.toTypedArray(),
            Tag(
                _tags.value!!.size + 1L,
                currentTime,
                _tags.value!!.lastOrNull()?.let { currentTime - it.measuredTime } ?: 0
            )
        ).toList()
    }
}