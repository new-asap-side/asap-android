package asap.aljyo.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val lightScheme = lightColorScheme(
    primary = AljyoColor.Red01,
    primaryContainer = AljyoColor.Red02,
    onPrimary = AljyoColor.White,
    surface = AljyoColor.Grey01,
    onSurface = AljyoColor.Black01
)

@Composable
fun AljyoTheme(
//    isDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalAljyoTypography provides independentTypography()) {
        MaterialTheme(
            colorScheme = lightScheme,
            content = content
        )
    }
}

object AljyoTheme {
    val typograpy: AljyoTypography
        @Composable
        get() = LocalAljyoTypography.current
}