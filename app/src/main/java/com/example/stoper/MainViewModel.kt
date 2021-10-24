package com.example.stoper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    var isTimerRunning = false
    var lastPause: Long? = null
    private val _tags = MutableLiveData<MutableList<Tag>>(mutableListOf())
    val tags: LiveData<MutableList<Tag>>
        get() = _tags

    fun addTag(tag: Tag){
        _tags.value?.add(tag)
        _tags.notifyObserver()
    }

    fun clearTags() {
        _tags.value?.clear()
        _tags.notifyObserver()
    }

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }
}