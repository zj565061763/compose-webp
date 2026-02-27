package com.sd.lib.compose.webp

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource

@Composable
fun WebpView(
  modifier: Modifier = Modifier,
  @DrawableRes resId: Int,
  contentDescription: String? = null,
  contentScale: ContentScale = ContentScale.Fit,
) {
  WebpView(
    modifier = modifier,
    controller = rememberWebpControllerWithResource(resId) { startPlay() },
    contentDescription = contentDescription,
    contentScale = contentScale,
  )
}

@Composable
fun WebpView(
  modifier: Modifier = Modifier,
  controller: WebpController,
  contentDescription: String? = null,
  contentScale: ContentScale = ContentScale.Fit,
  @DrawableRes previewResId: Int = 0,
) {
  require(controller is WebpControllerImpl)

  if (LocalInspectionMode.current) {
    val resId = previewResId.takeIf { it != 0 } ?: (controller as? WebpControllerResource)?.resId
    Image(
      modifier = modifier,
      painter = if (resId != null) painterResource(resId) else EmptyPainter,
      contentDescription = contentDescription,
      contentScale = contentScale,
    )
    return
  }

  Image(
    modifier = modifier,
    painter = rememberDrawablePainter(controller.getDrawable()),
    contentDescription = contentDescription,
    contentScale = contentScale,
  )
}