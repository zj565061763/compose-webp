package com.sd.lib.compose.webp

import android.graphics.drawable.Drawable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

interface WebpController {
  /** 是否正在播放 */
  fun isPlaying(): Boolean

  /** 开始播放 */
  fun startPlay()

  /** 停止播放 */
  fun stopPlay()

  /** 设置循环播放次数，默认0-无限循环 */
  fun setLoopCount(loopCount: Int)
}

internal open class WebpControllerImpl : WebpController {
  private var _drawable by mutableStateOf<CustomWebPDrawable?>(null)

  @Volatile
  private var _shouldPlay = false

  @Volatile
  private var _loopCount = 0

  fun getDrawable(): Drawable? {
    return _drawable
  }

  fun setDrawable(drawable: CustomWebPDrawable?) {
    if (_drawable != drawable) {
      _drawable?.stop()
      _drawable = drawable
      updatePlayState()
    }
  }

  override fun isPlaying(): Boolean {
    return _drawable?.isRunning == true
  }

  override fun startPlay() {
    _shouldPlay = true
    updatePlayState()
  }

  override fun stopPlay() {
    _shouldPlay = false
    updatePlayState()
  }

  override fun setLoopCount(loopCount: Int) {
    _loopCount = loopCount
    _drawable?.setLoopLimit(loopCount)
  }

  private fun updatePlayState() {
    _drawable?.setLoopLimit(_loopCount)
    if (_shouldPlay) {
      _drawable?.start()
    } else {
      _drawable?.stopAtFirstFrame()
    }
  }
}