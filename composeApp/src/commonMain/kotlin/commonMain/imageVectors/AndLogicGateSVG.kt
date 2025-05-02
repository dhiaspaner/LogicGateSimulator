package commonMain.imageVectors
import androidx.compose.ui.graphics.Path


val AndLogicGateSVG = Path().apply {
    // Main shape shifted by -105, -105

    moveTo(0f, 0f)
    verticalLineToRelative(302f)
    horizontalLineToRelative(151f)
    curveToRelative(148f, 0f, 148f, -302f, 0f, -302f)
    lineTo(0f, 0f)
    close()

    // First small horizontal bar (was at y=151, now y=46)
    moveTo(-89f, 46f)
    verticalLineToRelative(18f)
    horizontalLineToRelative(71f)
    verticalLineToRelative(-18f)
    lineTo(-89f, 46f)
    close()

    // Right side curve detail (was at y=247, now y=142)
    moveTo(279.8f, 142f)
    curveToRelative(0.2f, 6f, 0.2f, 12f, 0f, 18f)
    lineTo(391f, 160f)
    verticalLineToRelative(-18f)
    lineTo(279.8f, 142f)
    close()

    // Bottom small horizontal bar (was at y=343, now y=238)
    moveTo(-89f, 238f)
    verticalLineToRelative(18f)
    horizontalLineToRelative(71f)
    verticalLineToRelative(-18f)
    lineTo(-89f, 238f)
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
