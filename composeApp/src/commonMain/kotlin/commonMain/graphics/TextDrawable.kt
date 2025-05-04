package commonMain.graphics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.Density

interface TextDrawable : Drawable {

    var text: String
    var textStyle: TextStyle


    var textLayoutResult: TextLayoutResult?


    @Composable
    fun rememberMeasureText(
        textMeasurer: TextMeasurer,
        density: Density,
    ): TextLayoutResult = remember(key1 = text, key2 = style) {
        textMeasurer.measure(
            text = AnnotatedString(text),
            style = textStyle,
            density =density
        ).also {
            textLayoutResult = it
        }
    }


    fun drawText(drawScope: DrawScope) {
        textLayoutResult?.let {
            with(drawScope) {
                drawText(
                    textLayoutResult = it,
                    topLeft = Offset.Zero
                )
            }
        }
    }
}
