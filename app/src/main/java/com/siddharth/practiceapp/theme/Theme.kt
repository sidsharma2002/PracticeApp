package com.siddharth.practiceapp.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import com.siddharth.practiceapp.ui.fragments.ui.theme.*

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple200,
    onSecondary = Black,
    secondary = secondaryBlack,
    onPrimary = white,
    background = Black,
    onBackground = secondaryBlack
)

@Composable
fun PracticeAppTheme(
    content: @Composable() () -> Unit
) {
    val colors = DarkColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}