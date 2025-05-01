package commonMain.graphics

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

interface TextDrawable : Drawable {

    var text: String
    var fontSize: Float
    var color: Color
    var fontFamily: FontFamily
    var fontStyle: FontStyle

    var textLayoutResult: TextLayoutResult?

    fun createTextLayout(
        density: Density,
        textStyle: TextStyle,
        fontFamilyResolver: FontFamily.Resolver,
        constraints: Constraints
    ): TextLayoutResult {


        val textMeasurer = TextMeasurer(
            defaultDensity = density,
            defaultFontFamilyResolver = fontFamilyResolver,
            defaultLayoutDirection = LayoutDirection.Ltr,
        )

        val textLayoutResult = textMeasurer.measure(
            text = AnnotatedString(text),
            style = textStyle,
            constraints = constraints,
            overflow = TextOverflow.Clip,
            softWrap = true
        )

        this.textLayoutResult = textLayoutResult
        return textLayoutResult

    }

    fun drawText(drawScope: DrawScope) {
        textLayoutResult?.let {
            drawScope.drawText(
                textLayoutResult = it,
                brush = Brush.verticalGradient()
            )
        }

    }

    override fun draw(drawScope: DrawScope) {
        super.draw(drawScope)
        drawText(drawScope)
    }

}
