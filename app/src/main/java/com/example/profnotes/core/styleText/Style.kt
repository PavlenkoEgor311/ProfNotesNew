package com.example.profnotes.core.styleText

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.profnotes.R

object Typo {
    private val CircleFontFamily = FontFamily(
        listOf(
            Font(R.font.circe_bold, FontWeight.Bold),
            Font(R.font.circe_extra_bold, FontWeight(800)),
            Font(R.font.circe_light, FontWeight.Light),
            Font(R.font.circe_regular, FontWeight.Normal),
            Font(R.font.circe_thin, FontWeight.Light),
        )
    )
    val DefaultTypography = Typography(
        defaultFontFamily = CircleFontFamily,
        h1 = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        ),
        h2 = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        ),
        h3 = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        ),
        h4 = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            letterSpacing = 0.sp
        ),
        h5 = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
        ),
        h6 = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            letterSpacing = 0.sp,
            lineHeight = 28.sp
        ),
    )
}