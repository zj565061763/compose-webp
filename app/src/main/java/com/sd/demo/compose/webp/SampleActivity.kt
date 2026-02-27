package com.sd.demo.compose.webp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sd.demo.compose.webp.theme.AppTheme
import com.sd.lib.compose.webp.WebpView
import com.sd.lib.compose.webp.rememberWebpControllerWithResource

class SampleActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      AppTheme {
        Content()
      }
    }
  }
}

@Composable
private fun Content(
  modifier: Modifier = Modifier,
) {
  val controller = rememberWebpControllerWithResource(R.drawable.anim_role_vertical_jump)

  Column(
    modifier = modifier
      .fillMaxSize()
      .safeDrawingPadding()
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    WebpView(
      modifier = Modifier
        .size(100.dp)
        .background(Color.Black),
      resId = R.drawable.anim_role_star_jump,
    )

    HorizontalDivider()

    WebpView(
      modifier = Modifier
        .size(100.dp)
        .background(Color.Black),
      controller = controller,
    )

    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
      // 开始播放
      Button(onClick = { controller.startPlay() }) {
        Text(text = "start")
      }
      // 停止播放
      Button(onClick = { controller.stopPlay() }) {
        Text(text = "stop")
      }
    }
  }
}

@Preview
@Composable
private fun Preview() {
  AppTheme {
    Content()
  }
}