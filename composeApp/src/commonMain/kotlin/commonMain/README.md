# Drag and Drop for Compose Multiplatform

This package provides a cross-platform drag-and-drop implementation for Compose Multiplatform that works on all platforms, including iOS.

## Features

- Cross-platform drag-and-drop functionality
- Works on Android, iOS, and Desktop
- Simple API for integrating with your Compose UI
- Supports dragging and dropping drawable elements

## Usage

### Basic Setup

1. Create a `DragAndDropManager` using the `rememberDragAndDropManager()` function:

```kotlin
val dragAndDropManager = rememberDragAndDropManager()
```

2. Apply the `draggable` modifier to your Composable:

```kotlin
Canvas(
    modifier = Modifier
        .fillMaxSize()
        .draggable(
            dragAndDropManager = dragAndDropManager,
            onDragStart = { offset ->
                // Find the drawable at the clicked position and start dragging it
                findDrawableAt(graphics, offset)?.let { drawable ->
                    dragAndDropManager.startDrag(drawable, offset)
                }
            }
        )
) {
    // Draw your graphics
    graphics.forEach { drawable ->
        drawable.draw(this)
    }
}
```

### Advanced Usage

You can customize the drag-and-drop behavior by providing callbacks for different drag events:

```kotlin
.draggable(
    dragAndDropManager = dragAndDropManager,
    onDragStart = { offset ->
        // Handle drag start
        findDrawableAt(graphics, offset)?.let { drawable ->
            dragAndDropManager.startDrag(drawable, offset)
        }
    },
    onDragEnd = {
        // Handle drag end
        println("Drag ended")
    },
    onDragCancel = {
        // Handle drag cancel
        println("Drag canceled")
    }
)
```

### Finding Drawables

The `findDrawableAt` function helps you find a drawable at a specific position:

```kotlin
val clickedDrawable = findDrawableAt(graphics, offset)
```

## Implementation Details

The drag-and-drop functionality is implemented using Compose's built-in pointer input handling, which works across all platforms, including iOS. On iOS, touch events are automatically translated to pointer events by the Compose framework.

The `DragAndDropManager` class manages the drag-and-drop state, including tracking the currently dragged item and handling drag operations. It provides methods for starting, updating, and ending drag operations.

## iOS Support

The implementation is designed to work seamlessly on iOS devices. It uses Compose's cross-platform pointer input handling, which automatically translates iOS touch events to pointer events.

For better iOS support, the implementation tracks the last known position of the dragged item, which is important for iOS touch events.
