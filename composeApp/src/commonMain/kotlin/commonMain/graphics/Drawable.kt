package commonMain.graphics

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.drawscope.DrawScope

interface Drawable {

    var style: Style
    val matrix: Matrix
    var size: Size

    val topLeft: Offset
        get() {
            val tx = matrix.values[6] + matrix.values[0]
            val ty = matrix.values[7] + matrix.values[4]
            return Offset(tx, ty)
        }

    fun drawLocal(drawScope: DrawScope)

    fun draw(drawScope: DrawScope) {
        with(drawScope.drawContext.canvas) {
            concat(matrix)
            drawLocal(drawScope)
        }
    }

}
