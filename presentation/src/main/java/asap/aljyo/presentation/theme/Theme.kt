package asap.aljyo.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

private val lightScheme = lightColorScheme(
    primary = AljyoColor.Red01,
    primaryContainer = AljyoColor.Red02,
    onPrimary = AljyoColor.White,
    surface = AljyoColor.Grey01,
    onSurface = AljyoColor.Black01
)

private val darkScheme = darkColorScheme(
    primary = AljyoColor.Red01,
    primaryContainer = AljyoColor.Red02,
    onPrimary = AljyoColor.White,
    surface = AljyoColor.Grey01,
    onSurface = AljyoColor.Black01
)

@Composable
fun AljyoTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if(isDark) darkScheme else lightScheme
    CompositionLocalProvider(LocalAljyoTypography provides independentTypography()) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}

object AljyoTheme {
    val typograpy: AljyoTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalAljyoTypography.current
}