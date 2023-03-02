package com.example.profnotes.core.colorCompose

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.profnotes.R


val MyFont= FontFamily(
    Font(R.font.circe_bold, FontWeight.Normal)
)

val textStyle = Typography(
    body1 = TextStyle(
        fontFamily = MyFont,
        fontSize = 16.sp
    ),
)