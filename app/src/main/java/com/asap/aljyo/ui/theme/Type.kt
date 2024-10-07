package com.asap.aljyo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.asap.aljyo.R

val pretendardFamily = FontFamily(
    Font(R.font.pretendard_regular, FontWeight.Normal),
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_semibold, FontWeight.SemiBold),
    Font(R.font.pretendard_bold, FontWeight.Bold),
)

val letterSpacing = 0.2.sp

val Typography = Typography(
    // Pretendard Bold
    headlineMedium = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Bold,
        letterSpacing = letterSpacing
    ),
    // Pretendard SemiBold
    titleMedium = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = letterSpacing
    ),
    // Pretendard Medium
    bodyMedium = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Medium,
        letterSpacing = letterSpacing
    ),
    //Pretendard Regular
    labelMedium = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight.Normal,
        letterSpacing = letterSpacing,
    ),
)

