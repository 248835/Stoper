package com.example.stoper.stopper

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.stoper.R

class Stopper : androidx.appcompat.widget.AppCompatTextView {
    private val viewModel: StopperViewModel =
        ViewModelProvider(context as ViewModelStoreOwner).get(StopperViewModel::class.java)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init { restart() }

    fun start() = viewModel.start { text ->
        this.text = text
    }

    fun stop() = viewModel.stop()

    fun restart() {
        viewModel.restart()
        text = context.getString(R.string.timeZero)
    }

    val tags = viewModel.tags

    fun addTag() = viewModel.addTag()

    fun isRunning() = viewModel.isRunning

}