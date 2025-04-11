package asap.aljyo.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import asap.aljyo.presentation.R

// 시스템 font size에 영향을 받지 않는 textUnit
val Int.fixed: TextUnit
    @Composable
    get() = (this / LocalDensity.current.fontScale).sp

val Float.fixed: TextUnit
    @Composable
    get() = (this / LocalDensity.current.fontScale).sp

//@Composable
//fun dpToSp(dp: Dp): TextUnit = with(LocalDensity.current) { dp.toSp() }

private val pretendard = FontFamily(
    Font(R.font.pretendard_regular, FontWeight.Normal),
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_semibold, FontWeight.SemiBold),
    Font(R.font.pretendard_bold, FontWeight.Bold),
)

@Composable
internal fun independentTypography(): AljyoTypography {
    return AljyoTypography(
        h1 = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 22.fixed,
            lineHeight = 30.fixed,
            letterSpacing = -(0.02f).fixed
        ),
        h2 = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 20.fixed,
            lineHeight = 28.fixed,
            letterSpacing = -(0.02f).fixed
        ),
        h3 = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 18.fixed,
            lineHeight = 26.fixed,
            letterSpacing = -(0.02f).fixed
        ),
        h4 = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 16.fixed,
            lineHeight = 24.fixed,
            letterSpacing = -(0.02f).fixed
        ),
        h5 = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.fixed,
            lineHeight = 24.fixed,
            letterSpacing = -(0.02f).fixed
        ),
        h6 = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 15.fixed,
            lineHeight = 24.fixed,
            letterSpacing = -(0.02f).fixed
        ),
        h7 = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 14.fixed,
            lineHeight = 22.fixed,
            letterSpacing = -(0.02f).fixed
        ),
    )
}

@Immutable
data class AljyoTypography(
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val h4: TextStyle,
    val h5: TextStyle,
    val h6: TextStyle,
    val h7: TextStyle,
//    val b1: TextStyle,
//    val b2: TextStyle,
//    val b3: TextStyle,
//    val b4: TextStyle,
//    val b5: TextStyle,
//    val b6: TextStyle,
//    val b7: TextStyle,
//    val c1: TextStyle,
//    val c2: TextStyle,
//    val c3: TextStyle,
)

val LocalAljyoTypography = staticCompositionLocalOf {
    AljyoTypography(
        h1 = TextStyle.Default,
        h2 = TextStyle.Default,
        h3 = TextStyle.Default,
        h4 = TextStyle.Default,
        h5 = TextStyle.Default,
        h6 = TextStyle.Default,
        h7 = TextStyle.Default,
    )
}