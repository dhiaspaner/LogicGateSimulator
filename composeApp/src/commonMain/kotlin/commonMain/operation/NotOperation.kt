package commonMain.operation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import commonMain.graphics.Drawable
import commonMain.graphics.Style
import commonMain.imageVectors.NotLogicGateSVG

class NotOperation(
    override val receivedSignal: Boolean,

) : Receptor.SingleReceptor, OutputType.SingleSignal, Drawable {

    override val path = NotLogicGateSVG
    override var matrix by mutableStateOf(value = Matrix())
    override var size: Size by mutableStateOf(value = Size(50f, 50f))

    init {
        path.alignTopLeftMatrix()
        adjustSizeWithPath()
    }

    override val outputSignal: Boolean get() = operate()

    override fun operate(): Boolean = receivedSignal.not()




    override var style: Style by mutableStateOf(
        value = Style(
            fillColor = Color.Black,
            strokeColor = Color.Black,
            strokeWidth = 1f
        )
    )

    override fun drawLocal(drawScope: DrawScope) {
        drawScope.drawPath(
            path,
            style  = Fill,
            color = style.fillColor
        )
    }


}
