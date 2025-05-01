package commonMain.imageVectors
import androidx.compose.ui.graphics.Path


val AndLogicGateSVG = Path().apply {
            moveTo(105f, 105f)
            verticalLineToRelative(302f)
            horizontalLineToRelative(151f)
            curveToRelative(148f, 0f, 148f, -302f, 0f, -302f)
            lineTo(105f, 105f)
            close()
            moveTo(16f, 151f)
            verticalLineToRelative(18f)
            horizontalLineToRelative(71f)
            verticalLineToRelative(-18f)
            lineTo(16f, 151f)
            close()
            moveTo(384.8f, 247f)
            curveToRelative(0.2f, 6f, 0.2f, 12f, 0f, 18f)
            lineTo(496f, 265f)
            verticalLineToRelative(-18f)
            lineTo(384.8f, 247f)
            close()
            moveTo(16f, 343f)
            verticalLineToRelative(18f)
            horizontalLineToRelative(71f)
            verticalLineToRelative(-18f)
            lineTo(16f, 343f)
            close()
        }


fun Path.verticalLineToRelative(dy: Float): Path {
    return this.apply {
        relativeLineTo(0f, dy)
    }
}

fun Path.horizontalLineToRelative(dx: Float): Path {
    return this.apply {
        relativeLineTo(dx, 0f)
    }
}

fun Path.curveToRelative(
    dx1: Float, dy1: Float,
    dx2: Float, dy2: Float,
    dx3: Float, dy3: Float
): Path {
    return this.apply {
        relativeCubicTo(dx1, dy1, dx2, dy2, dx3, dy3)
    }
}
