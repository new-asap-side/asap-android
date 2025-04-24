package asap.aljyo.presentation.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density

@Composable
fun FixedFontComponent(content: @Composable () -> Unit) {
    // Density fontScale 고정
    CompositionLocalProvider(
        LocalDensity provides Density(
            density = LocalDensity.current.density,
            fontScale = 1f
        )
    ) { content() }
}