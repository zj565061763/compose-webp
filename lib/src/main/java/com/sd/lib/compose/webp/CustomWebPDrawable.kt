package com.sd.lib.compose.webp

import android.content.Context
import com.github.penfeizhou.animation.FrameAnimationDrawable
import com.github.penfeizhou.animation.decode.Frame
import com.github.penfeizhou.animation.decode.FrameSeqDecoder.RenderListener
import com.github.penfeizhou.animation.loader.Loader
import com.github.penfeizhou.animation.loader.ResourceStreamLoader
import com.github.penfeizhou.animation.webp.decode.WebPDecoder
import com.github.penfeizhou.animation.webp.io.WebPReader
import com.github.penfeizhou.animation.webp.io.WebPWriter
import java.util.concurrent.atomic.AtomicBoolean

internal class CustomWebPDrawable private constructor(loader: Loader) : FrameAnimationDrawable<WebPDecoder>(loader) {
  private var _stopAtFirstFrame = AtomicBoolean()

  override fun createFrameSeqDecoder(streamLoader: Loader, listener: RenderListener?): WebPDecoder {
    return CustomWebPDecoder(streamLoader, listener)
  }

  fun stopAtFirstFrame() {
    if (_stopAtFirstFrame.compareAndSet(false, true)) {
      super.start()
    }
  }

  override fun start() {
    _stopAtFirstFrame.set(false)
    super.start()
  }

  override fun stop() {
    _stopAtFirstFrame.set(false)
    super.stop()
  }

  init {
    setAutoPlay(false)
  }

  private inner class CustomWebPDecoder(
    loader: Loader,
    listener: RenderListener?,
  ) : WebPDecoder(loader, listener) {

    override fun renderFrame(frame: Frame<WebPReader?, WebPWriter?>?) {
      super.renderFrame(frame)
      if (_stopAtFirstFrame.get() && frameIndex == 0) {
        pause()
      }
    }
  }

  companion object {
    fun fromResource(context: Context, resId: Int): CustomWebPDrawable {
      return CustomWebPDrawable(ResourceStreamLoader(context, resId))
    }
  }
}