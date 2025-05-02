package commonMain.operation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp
import commonMain.graphics.Style
import commonMain.graphics.TextDrawable
import commonMain.interactions.Interactable


class ConstantVoltage(
    type: Type,
    density: Density,
    fontFamilyResolver: FontFamily.Resolver,
) : OutputType.SingleSignal, TextDrawable, Interactable.Clickable{

    override var text: String by mutableStateOf(value = if (type == Type.HIGH) "1" else "0")

    override var fontSize: Float by mutableStateOf(value = 16f)
    override var color: Color by mutableStateOf(value = Color.Black)
    override var fontFamily: FontFamily by mutableStateOf(value = FontFamily.Default)
    override var fontStyle: FontStyle by mutableStateOf(value = FontStyle.Normal)

    enum class Type {
        HIGH,
        LOW
    }

    override var textLayoutResult: TextLayoutResult? = null

    init {
        createTextLayout(density, fontFamilyResolver)
    }



    var type: Type = type
        set(value) {
            updateTextByType(value)
            field = value
        }

    override val outputSignal: Boolean
        get() = operate()



    override fun operate(): Boolean {
        return when (type) {
            Type.HIGH -> true
            Type.LOW -> false
        }
    }

    override val matrix: Matrix by mutableStateOf(value = Matrix())
    override var size: Size by mutableStateOf(value = Size(50f, 50f))
    override var style: Style by mutableStateOf(
        value = Style(
            fillColor = Color.Black,
            strokeColor = Color.Black,
            strokeWidth = 1f
        )
    )
    override val path = Path()

    fun createTextLayout(density: Density, fontFamilyResolver: FontFamily.Resolver): TextLayoutResult {
        val text = "1"
        val style = TextStyle(
            fontSize = 16.sp,
            fontFamily = FontFamily.Default,
            textAlign = TextAlign.Left
        )


        val textMeasurer = TextMeasurer(
            defaultDensity = density,
            defaultFontFamilyResolver = fontFamilyResolver,
            defaultLayoutDirection = LayoutDirection.Ltr,
        )

        val layoutResult = textMeasurer.measure(
            text = AnnotatedString(text),
            style = style,
            constraints = Constraints(maxWidth = 500),
            overflow = TextOverflow.Clip,
            softWrap = true
        )

        this.textLayoutResult = layoutResult

        return layoutResult
    }


    override fun drawLocal(drawScope: DrawScope) {
        println("drawing constant voltage")
        drawScope.drawRect(
            color = style.strokeColor,
            size = size,
            topLeft = Offset(0f, 0f),
            style = Stroke(width = style.strokeWidth)
        )
        drawText(drawScope)
    }

    override fun drawText(drawScope: DrawScope) {
        textLayoutResult?.let {
            println("Drawing text")
            drawScope.drawText(
                textLayoutResult = it,
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Red, Color.Blue),
                    startY = 0f,
                    endY = size.height
                ),
                topLeft = Offset(size.width / 2 - it.size.width / 2, size.height / 2 - it.size.height / 2),
                alpha = 1f,
                shadow = null,
                textDecoration = null,
                drawStyle = Fill,
                blendMode = DrawScope.DefaultBlendMode
            )
        }
    }

    override fun onClick(offset: Offset) {
        type = when (type) {
            Type.HIGH -> Type.LOW
            Type.LOW -> Type.HIGH
        }
    }

    private fun updateTextByType(type: Type = this.type)  {
        text =  when (type) {
            Type.HIGH -> "1"
            Type.LOW -> "0"
        }

    }



}
