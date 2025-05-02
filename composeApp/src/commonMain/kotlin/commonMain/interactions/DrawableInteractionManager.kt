package commonMain.interactions

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import commonMain.graphics.Drawable

class DrawableInteractionManager {

    var selectedDrawings = listOf<Drawable>()

    var dragStartOffset: Offset = Offset.Unspecified
        private set
    var dragOffset: Offset = Offset.Unspecified
        private set

    var hoverOffset: Offset = Offset.Unspecified

    var updateUi by mutableIntStateOf(-1)


    fun onHitTest(
        offset: Offset,
        drawableList: List<Drawable>
    ): List<Drawable> {

        return drawableList.filter {
            val boundingBox = it.path.getBounds().translate(it.topLeft)
            offset in boundingBox
        }.also {
            updateUi++
        }
    }

    fun onClick(
        offset: Offset,
        drawableList: List<Drawable>
    ) {
        selectedDrawings = emptyList()
        selectedDrawings = onHitTest(offset, drawableList)
            .apply {
                filterIsInstance<Interactable.Clickable>()
                    .forEach { it.onClick(offset) }
            }
        updateUi++
    }

    fun onDragStart(
        offset: Offset,
        drawableList: List<Drawable>
    ) {
        dragStartOffset = offset
        dragOffset = offset
        onHitTest(dragStartOffset, drawableList).run {
            val itemToDrag = reversed().firstOrNull()
            itemToDrag?.let { selectedDrawings += it }
            filterIsInstance<Interactable.Draggable>()
                .forEach { it.onDragStart(dragStartOffset) }
        }
        updateUi++
    }

    fun onDrag(offset: Offset) {
        val change = offset - dragOffset
        print("change $change")
        selectedDrawings.forEach {

            it.translateBy(offset = change)
        }
        selectedDrawings
            .filterIsInstance<Interactable.Draggable>()
            .forEach { it.onDrag(change) }

        dragOffset = offset


        updateUi++
    }

    fun onDragEnd() {
        selectedDrawings
            .filterIsInstance<Interactable.Draggable>()
            .forEach { it.onDragEnd(dragOffset) }

        selectedDrawings = emptyList()
    }

    fun onDragCancel() {
        selectedDrawings
            .filterIsInstance<Interactable.Draggable>()
            .forEach { it.onDragCancel() }
        selectedDrawings = emptyList()
        updateUi++
    }

}

@Composable
fun rememberDrawableInteractionManager(): DrawableInteractionManager {
    return remember {
        DrawableInteractionManager()
    }
}


@Composable
fun Modifier.draggable(
    drawableList: List<Drawable>,
    drawableInteractionManager: DrawableInteractionManager,
    onDragStart: (Offset) -> Unit = {},
    onDragEnd: () -> Unit = {},
    onDragCancel: () -> Unit = {},
    enabled: Boolean = true
): Modifier {
    return this.pointerInput(enabled) {
        if (enabled) {
            detectDragGestures(
                onDragStart = { offset ->
                    drawableInteractionManager.onDragStart(offset, drawableList)
                    // Handle drag start
                    onDragStart(offset)
                },
                onDragEnd = {
                    // Handle drag end
                    drawableInteractionManager.onDragEnd()
                    onDragEnd()
                },
                onDragCancel = {
                    // Handle drag cancel
                    drawableInteractionManager.onDragCancel()
                    onDragCancel()
                },
                onDrag = { change, dragAmount ->
                    // Consume the gesture to prevent other handlers from processing it
                    change.consume()

                    // Update the position with the drag amount
                    drawableInteractionManager.onDrag(change.position,)
                }
            )
        }
    }
}
