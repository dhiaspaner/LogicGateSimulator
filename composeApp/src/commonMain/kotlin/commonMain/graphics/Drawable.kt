package commonMain.graphics

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.text.TextLayoutResult

interface Drawable  {

    var style: Style
    val matrix: Matrix
    var size: Size
    val path: Path

    val topLeft: Offset
        get() {
            val tx = matrix.values[12]
            val ty = matrix.values[13]
            return Offset(tx, ty)
        }

    fun drawLocal(drawScope: DrawScope) {
        drawScope.drawPath(
            path,
            style = Stroke(width = style.strokeWidth),
            color = style.strokeColor
        )
    }


    fun draw(drawScope: DrawScope) {
        applyTransformationMatrix(drawScope) {
            drawLocal(drawScope)
        }
    }

    fun applyTransformationMatrix(drawScope: DrawScope, onDraw: DrawScope.() -> Unit) {
        drawScope.withTransform(
            transformBlock =  {
                transform(matrix)
            } ,
            drawBlock = {
                onDraw()
            }
        )
    }

    fun drawBounds(drawScope: DrawScope) {
        applyTransformationMatrix(drawScope) {
            drawContext.canvas.drawRect(
                rect = path.getBounds(),
                paint = androidx.compose.ui.graphics.Paint().apply {
                    color = Color.Blue
                    style = androidx.compose.ui.graphics.PaintingStyle.Stroke
                    strokeWidth = 2f
                }
            )

        }
    }

    fun Path.alignTopLeftMatrix() {
        val bounds = this.getBounds()
        matrix.translate(-bounds.left, -bounds.top)
    }


    fun adjustSizeWithPath() {
        val bounds = path.getBounds()
        size = Size(
            width = bounds.width,
            height = bounds.height
        )
    }

    fun rotateBy(angleX: Float, angleY: Float, angleZ: Float) {
        matrix.apply {
            rotateX(angleX)
            rotateY(angleY)
            rotateZ(angleZ)
        }
    }

    fun scaleBy(scaleX: Float, scaleY: Float) {
        matrix.scale(scaleX, scaleY)
    }

    fun scaleBy(scale: Float) {
        matrix.scale(scale, scale)
    }

    fun translateBy(offset: Offset) {
        matrix.translate(offset.x, offset.y)
    }

}
