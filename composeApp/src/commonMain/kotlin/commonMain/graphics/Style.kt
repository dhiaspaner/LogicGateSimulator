package commonMain.graphics

import androidx.compose.ui.graphics.Color

data class Style(
    val fillColor: Color = Color.Transparent,
    val strokeColor: Color = Color.Transparent,
    val strokeWidth: Float = 1f,
)
