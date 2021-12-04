package org.ethereum.dappstore.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color(0xFF5450E9),
    primaryVariant = Color(0xFF3C39B1),
    secondary = Color(0xFFA2A0F8),
    background = Color(0xFF232323),
    surface = Color(0xFF323232),
)

private val LightColorPalette = lightColors(
    primary = Color(0xFF181ED6),
    primaryVariant = Color(0xFF060A7C),
    secondary = Color(0xFF1874D6)

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun DAppStoreTheme(darkTheme: Boolean = true, content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}