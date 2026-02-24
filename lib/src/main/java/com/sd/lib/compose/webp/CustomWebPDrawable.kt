package com.sd.lib.compose.webp

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.github.penfeizhou.animation.FrameAnimationDrawable
import com.github.penfeizhou.animation.decode.FrameSeqDecoder
import com.github.penfeizhou.animation.decode.FrameSeqDecoder.RenderListener
import com.github.penfeizhou.animation.loader.Loader
import com.github.penfeizhou.animation.loader.ResourceStreamLoader
import com.github.penfeizhou.animation.webp.decode.WebPDecoder

internal class CustomWebPDrawable private constructor(loader: Loader) : FrameAnimationDrawable<WebPDecoder>(loader) {
  private val _workerHandler by lazy {
    FrameSeqDecoder::class.java.getDeclaredField("workerHandler")
      .apply { isAccessible = true }
      .let { it.get(frameSeqDecoder) as Handler }
  }

  private val _bitmapField by lazy {
    FrameAnimationDrawable::class.java.getDeclaredField("bitmap")
      .apply { isAccessible = true }
  }

  private val _mainHandler = Handler(Looper.getMainLooper())

  override fun createFrameSeqDecoder(streamLoader: Loader, listener: RenderListener?): WebPDecoder {
    return WebPDecoder(streamLoader, listener)
  }

  override fun setAutoPlay(autoPlay: Boolean) {
    super.setAutoPlay(false)
  }

  override fun stop() {
    val isRunning = isRunning
    super.stop()
    if (isRunning) {
      _workerHandler.post {
        val bitmap = frameSeqDecoder.getFrameBitmap(0)
        if (bitmap != null) {
          _bitmapField.set(this, bitmap)
          _mainHandler.post { invalidateSelf() }
        }
      }
    }
  }

  init {
    setAutoPlay(false)
  }

  companion object {
    fun fromResource(context: Context, resId: Int): CustomWebPDrawable {
      return CustomWebPDrawable(ResourceStreamLoader(context, resId))
    }
  }
}