package commonMain.ui.operationType


import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke




fun DrawScope.drawNotUi() {
    val width = size.width
    val height = size.height

    val triangle = Path().apply {
        moveTo(width * 0.2f, height * 0.2f)
        lineTo(width * 0.2f, height * 0.8f)
        lineTo(width * 0.8f, height * 0.5f)
        close()
    }

    drawPath(triangle, color = Color.Black)

    drawCircle(
        color = Color.Black,
        radius = width * 0.05f,
        center = Offset(width * 0.8f + width * 0.04f , height * 0.5f)
    )
}


fun DrawScope.drawAndUi() {
    val width = size.width
    val height = size.height

    // draw red rectangle
    drawRoundRect(
        color = Color.Black,
        size = size,
        topLeft = Offset(width * 0.2f, height * 0.2f),
        style = Stroke(width = width * 0.05f),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius.Zero
    )
}

fun DrawScope.drawOrUi() {
    val width = size.width
    val height = size.height

    val path = Path().apply {
        moveTo(width * 0.2f, height * 0.2f)
        quadraticBezierTo(width * 0.4f, height * 0.5f, width * 0.2f, height * 0.8f)
        quadraticBezierTo(width * 0.5f, height * 0.8f, width * 0.9f, height * 0.5f)
        quadraticBezierTo(width * 0.5f, height * 0.2f, width * 0.2f, height * 0.2f)
        close()
    }

    drawPath(path, color = Color.Black)
}

fun DrawScope.drawNandUi() {
    drawAndUi()

    // Draw small inversion circle at the output
    val width = size.width
    val height = size.height
    drawCircle(
        color = Color.Black,
        radius = width * 0.05f,
        center = Offset(width * 0.95f, height * 0.5f)
    )
}

fun DrawScope.drawNorUi() {
    drawOrUi()

    // Draw small inversion circle at the output
    val width = size.width
    val height = size.height
    drawCircle(
        color = Color.Black,
        radius = width * 0.05f,
        center = Offset(width * 0.95f, height * 0.5f)
    )
}

fun DrawScope.drawXorUi() {
    val width = size.width
    val height = size.height

    val baseX = width * 0.2f
    val offsetX = width * 0.05f

    // XOR = OR with extra curve
    val background = Path().apply {
        moveTo(baseX - offsetX, height * 0.2f)
        quadraticTo(width * 0.4f - offsetX, height * 0.5f, baseX - offsetX, height * 0.8f)
    }

    val path = Path().apply {
        moveTo(baseX, height * 0.2f)
        quadraticBezierTo(width * 0.4f, height * 0.5f, baseX, height * 0.8f)
        quadraticBezierTo(width * 0.5f, height * 0.8f, width * 0.9f, height * 0.5f)
        quadraticBezierTo(width * 0.5f, height * 0.2f, baseX, height * 0.2f)
        close()
    }

    drawPath(background, color = Color.Black)
    drawPath(path, color = Color.Black)
}
