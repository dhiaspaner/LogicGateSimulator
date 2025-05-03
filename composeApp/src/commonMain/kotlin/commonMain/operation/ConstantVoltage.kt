package commonMain.operation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import commonMain.graphics.Style
import commonMain.graphics.TextDrawable
import commonMain.interactions.Interactable


class ConstantVoltage(
    type: Type,
) : OutputType.SingleSignal, TextDrawable, Interactable.Clickable {

    override var text: String by mutableStateOf(value = if (type == Type.HIGH) "1" else "0")


    override var textStyle by mutableStateOf(
        value = TextStyle(
            color = Color.Black,
            fontSize = 16.sp,
            fontFamily = FontFamily.Default,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center,
            lineHeight = 16.sp,
            letterSpacing = 0.sp,
        )
    )


    override var textLayoutResult: TextLayoutResult? by mutableStateOf(value = null)

    enum class Type {
        HIGH,
        LOW
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


    override val path = Path().apply {
        addRect(Rect(Offset.Zero, size))
    }

    init {
        path.alignTopLeftMatrix()
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


    override fun drawLocal(drawScope: DrawScope) {
        drawScope.drawPath(
            path = path,
            style = Stroke(width = style.strokeWidth),
            color = style.strokeColor
        )

        drawText(drawScope)

    }

    override fun drawText(drawScope: DrawScope) {
        val (width, height) = this.size
        textLayoutResult?.let {
            drawScope.drawText(
                textLayoutResult = it,
                topLeft = Offset(
                    x = (width - it.size.width) / 2,
                    y = (height - it.size.height) / 2
                ),
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
