package com.sd.lib.compose.webp

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberWebpControllerWithResource(
  @DrawableRes resId: Int,
  init: WebpController.() -> Unit = {},
): WebpController {
  return remember {
    WebpControllerResource().apply {
      startPlay()
      init()
    }
  }.also { controller ->
    controller.resId = resId
    val context = LocalContext.current
    DisposableEffect(resId) {
      val drawable = CustomWebPDrawable.fromResource(context, resId)
      controller.setDrawable(drawable)
      onDispose { controller.setDrawable(null) }
    }
  }
}

internal class WebpControllerResource : WebpControllerImpl() {
  var resId by mutableStateOf<Int?>(null)
}