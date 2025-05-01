package commonMain

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import platform.UIKit.UIGestureRecognizerStateEnded
import platform.UIKit.UIGestureRecognizerStateBegan
import platform.UIKit.UIGestureRecognizerStateChanged

/**
 * iOS-specific implementation of the draggable modifier.
 * This implementation ensures proper handling of drag gestures on iOS devices.
 */
@Composable
actual fun Modifier.draggable(
    dragAndDropManager: DragAndDropManager,
    onDragStart: (Offset) -> Unit,
    onDragEnd: () -> Unit,
    onDragCancel: () -> Unit,
    enabled: Boolean
): Modifier {
    return this.pointerInput(enabled) {
        if (enabled) {
            // Use the iOS-specific gesture recognizer state to handle drag gestures
            detectIOSDragGestures(
                onDragStart = { offset ->
                    onDragStart(offset)
                },
                onDragEnd = {
                    dragAndDropManager.endDrag()
                    onDragEnd()
                },
                onDragCancel = {
                    dragAndDropManager.endDrag()
                    onDragCancel()
                },
                onDrag = { change, dragAmount ->
                    change.consume()
                    dragAndDropManager.updateDragPosition(dragAmount)
                }
            )
        }
    }
}

/**
 * iOS-specific implementation of drag gesture detection.
 * This function handles the iOS gesture recognizer states to properly detect drag gestures.
 */
private suspend fun PointerInputScope.detectIOSDragGestures(
    onDragStart: (Offset) -> Unit = { },
    onDragEnd: () -> Unit = { },
    onDragCancel: () -> Unit = { },
    onDrag: (change: PointerInputChange, dragAmount: Offset) -> Unit = { _, _ -> }
) {
    awaitEachGesture {
        val down = awaitFirstDown(requireUnconsumed = false)
        var drag: PointerInputChange? = null

        onDragStart(down.position)

        do {
            drag = awaitPointerEvent().changes.firstOrNull()

            if (drag != null && drag.pressed) {
                onDrag(drag, drag.positionChange())
            }
        } while (drag != null && drag.pressed)

        if (drag?.pressed == false) {
            onDragEnd()
        } else {
            onDragCancel()
        }
    }
}
