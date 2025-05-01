package commonMain

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import commonMain.graphics.Drawable

/**
 * A utility class for handling drag-and-drop operations in a cross-platform way,
 * including iOS support.
 */
class DragAndDropManager {
    // Track the currently dragged item
    private var currentlyDraggedItem: Drawable? = null

    // Track the initial position when drag starts
    private var dragStartPosition = Offset.Zero

    // Track whether we're currently dragging
    private val isDragging = mutableStateOf(false)

    // Track the last known position to handle iOS touch events better
    private var lastKnownPosition = Offset.Zero

    /**
     * Start dragging an item
     */
    fun startDrag(item: Drawable, position: Offset) {
        currentlyDraggedItem = item
        dragStartPosition = position
        lastKnownPosition = position
        isDragging.value = true
    }

    /**
     * Update the position during drag
     */
    fun updateDragPosition(delta: Offset) {
        currentlyDraggedItem?.let { drawable ->
            // Apply the translation to the drawable's matrix
            drawable.matrix.translate(delta.x, delta.y, 0f)

            // Update last known position
            lastKnownPosition = Offset(
                lastKnownPosition.x + delta.x,
                lastKnownPosition.y + delta.y
            )
        }
    }

    /**
     * End the drag operation
     */
    fun endDrag() {
        currentlyDraggedItem = null
        isDragging.value = false
    }

    /**
     * Check if we're currently dragging
     */
    fun isDragging(): Boolean = isDragging.value

    /**
     * Get the currently dragged item
     */
    fun getCurrentlyDraggedItem(): Drawable? = currentlyDraggedItem

    /**
     * Get the last known position
     */
    fun getLastKnownPosition(): Offset = lastKnownPosition
}

/**
 * A Composable modifier that enables drag-and-drop functionality for drawables.
 * This works across all platforms including iOS.
 *
 * The implementation uses Compose's built-in pointer input handling which is designed
 * to work across all platforms, including iOS. On iOS, touch events are automatically
 * translated to pointer events by the Compose framework.
 */
@Composable
fun Modifier.draggable(
    dragAndDropManager: DragAndDropManager,
    onDragStart: (Offset) -> Unit = {},
    onDragEnd: () -> Unit = {},
    onDragCancel: () -> Unit = {},
    enabled: Boolean = true
): Modifier {
    return this.pointerInput(enabled) {
        if (enabled) {
            detectDragGestures(
                onDragStart = { offset ->
                    // Handle drag start
                    onDragStart(offset)
                },
                onDragEnd = {
                    // Handle drag end
                    dragAndDropManager.endDrag()
                    onDragEnd()
                },
                onDragCancel = {
                    // Handle drag cancel
                    dragAndDropManager.endDrag()
                    onDragCancel()
                },
                onDrag = { change, dragAmount ->
                    // Consume the gesture to prevent other handlers from processing it
                    change.consume()

                    // Update the position with the drag amount
                    dragAndDropManager.updateDragPosition(dragAmount)
                }
            )
        }
    }
}

/**
 * Create and remember a DragAndDropManager instance
 */
@Composable
fun rememberDragAndDropManager(): DragAndDropManager {
    return remember { DragAndDropManager() }
}

/**
 * Find a drawable at the given position
 */
fun findDrawableAt(drawables: List<Drawable>, position: Offset): Drawable? {
    // Reverse the list to check from top to bottom (last drawn is on top)
    return drawables.reversed().firstOrNull { drawable ->
        val topLeft = drawable.topLeft
        val size = drawable.size
        println("position: $position, topLeft: $topLeft, size: $size")
        position.x >= topLeft.x &&
        position.x <= topLeft.x + size.width &&
        position.y >= topLeft.y &&
        position.y <= topLeft.y + size.height
    }
}
