package commonMain.interactions

import androidx.compose.ui.geometry.Offset

sealed interface Interactable {

    interface Clickable : Interactable {
        fun onClick(offset: Offset)
    }

    interface Draggable : Interactable {
        fun onDragStart(offset: Offset)
        fun onDrag(dragAmount: Offset)
        fun onDragEnd(offset: Offset)
        fun onDragCancel()
    }

    interface Hoverable : Interactable {
        fun onHover(offset: Offset)
    }

}
