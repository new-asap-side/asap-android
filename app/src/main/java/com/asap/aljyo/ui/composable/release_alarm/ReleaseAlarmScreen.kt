package com.asap.aljyo.ui.composable.release_alarm

import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.asap.aljyo.R
import com.asap.aljyo.ui.theme.AljyoTheme
import com.asap.aljyo.ui.theme.White
import kotlin.random.Random
import androidx.compose.ui.graphics.Color as compose

private val colors = listOf(
    compose(0xFFFFDF8F),
    compose(0xFFFF999E),
    compose(0xFFFF5FA9),
    compose(0xFFFF5FA9),
)

private val illusts = listOf(
    R.drawable.img_illust_water_drop,
    R.drawable.img_illust_flower,
    R.drawable.img_illust_rainbow,
    R.drawable.img_illust_cloud,
)


@Composable
internal fun ReleaseAlarmScreen(
    navigateToResult: (Int) -> Unit
) {
    val index by rememberSaveable {
        mutableIntStateOf(Random.run { nextInt(illusts.size) })
    }
    val navigateToResultByIndex = { navigateToResult(index) }

    val context = LocalContext.current

    SideEffect {
        val activity = context as ComponentActivity
        activity.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )

        val window = activity.window
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    AljyoTheme {
        Scaffold { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = Brush.linearGradient(colors))
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 40.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "09:30",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = 86.sp,
                            color = White
                        ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(50.dp))

                    Text(
                        text = stringResource(R.string.drag_description),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 24.sp,
                            color = White
                        ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = stringResource(R.string.drag_to_release_alarm),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 18.sp,
                            color = White
                        )
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    DragArea(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        resourceId = illusts[index],
                        navigateToResult = navigateToResultByIndex,
                    )
                }

            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(colors))
    )
}