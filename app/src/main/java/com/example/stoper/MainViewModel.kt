package com.example.stoper

import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    var isTimerRunning = false
    var lastPause: Long? = null
    val tags: MutableList<Tag> = mutableListOf()
}