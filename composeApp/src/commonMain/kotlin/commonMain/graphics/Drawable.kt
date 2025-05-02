package commonMain.graphics

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import commonMain.interactions.Interactable

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
        drawInLocalSpace(drawScope) {
            drawLocal(drawScope)
        }
    }

    fun drawInLocalSpace(drawScope: DrawScope, onDraw: DrawScope.() -> Unit) {
        with(drawScope.drawContext.canvas) {
            save()
            concat(matrix)
            onDraw(drawScope)
            restore()
        }
    }

    fun drawBounds(drawScope: DrawScope) {
        drawInLocalSpace(drawScope) {
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
        matrix.apply {
            reset()
            translate(-bounds.left, -bounds.top)
        }
        transform(matrix)
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
            reset()
            rotateX(angleX)
            rotateY(angleY)
            rotateZ(angleZ)
        }
        path.transform(matrix)
    }

    fun scaleBy(scaleX: Float, scaleY: Float) {
        matrix.apply {
            reset()
            scale(scaleX, scaleY)
        }
        path.transform(matrix)
    }

    fun scaleBy(scale: Float) {
        matrix.apply {
            reset()
            scale(scale, scale)
        }
        path.transform(matrix)
    }

    fun translateBy(offset: Offset) {
        matrix.apply {
            reset()
            translate(offset.x, offset.y)
        }
        path.transform(matrix)
    }

}
