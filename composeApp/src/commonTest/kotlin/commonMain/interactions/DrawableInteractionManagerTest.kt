package commonMain.interactions

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import commonMain.graphics.Drawable
import commonMain.graphics.Style
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DrawableInteractionManagerTest {

    private lateinit var manager: DrawableInteractionManager
    private lateinit var mockDrawable1: MockDrawable
    private lateinit var mockDrawable2: MockDrawable
    private lateinit var mockClickable: MockClickableDrawable
    private lateinit var mockDraggable: MockDraggableDrawable
    private lateinit var drawableList: List<Drawable>

    @BeforeTest
    fun setup() {
        manager = DrawableInteractionManager()

        // Create mock drawables at different positions
        mockDrawable1 = MockDrawable(Offset(10f, 10f), Size(50f, 50f))
        mockDrawable2 = MockDrawable(Offset(100f, 100f), Size(50f, 50f))
        mockClickable = MockClickableDrawable(Offset(200f, 200f), Size(50f, 50f))
        mockDraggable = MockDraggableDrawable(Offset(300f, 300f), Size(50f, 50f))

        drawableList = listOf(mockDrawable1, mockDrawable2, mockClickable, mockDraggable)
    }

    @Test
    fun testOnHitTest_shouldReturnDrawablesAtOffset() {
        // Test hit on first drawable
        val insideOffset1 = Offset(30f, 30f)
        val result1 = manager.onHitTest(insideOffset1, drawableList)
        assertEquals(1, result1.size)
        assertTrue(result1.contains(mockDrawable1))

        // Test hit on second drawable
        val insideOffset2 = Offset(120f, 120f)
        val result2 = manager.onHitTest(insideOffset2, drawableList)
        assertEquals(1, result2.size)
        assertTrue(result2.contains(mockDrawable2))

        // Test miss (no hit)
        val outsideOffset = Offset(500f, 500f)
        val result3 = manager.onHitTest(outsideOffset, drawableList)
        assertTrue(result3.isEmpty())
    }

    @Test
    fun testOnClick_shouldSelectDrawablesAndTriggerClickable() {
        // Click on clickable drawable
        val clickOffset = Offset(220f, 220f)
        manager.onClick(clickOffset, drawableList)

        // Verify selection
        assertEquals(1, manager.selectedDrawings.size)
        assertTrue(manager.selectedDrawings.contains(mockClickable))

        // Verify onClick was called
        assertTrue(mockClickable.onClickCalled)
        assertEquals(clickOffset, mockClickable.lastClickOffset)
    }

    @Test
    fun testOnDragStart_shouldSetDragOffsetsAndSelectDrawable() {
        // Start drag on draggable drawable
        val dragStartOffset = Offset(320f, 320f)
        manager.onDragStart(dragStartOffset, drawableList)

        // Verify drag offsets
        assertEquals(dragStartOffset, manager.dragStartOffset)
        assertEquals(dragStartOffset, manager.dragOffset)

        // Verify selection
        assertTrue(manager.selectedDrawings.contains(mockDraggable))

        // Verify onDragStart was called
        assertTrue(mockDraggable.onDragStartCalled)
        assertEquals(dragStartOffset, mockDraggable.lastDragStartOffset)
    }

    @Test
    fun testOnDrag_shouldMoveSelectedDrawablesAndCallDragMethod() {
        // Setup: first select a drawable
        val dragStartOffset = Offset(320f, 320f)
        manager.onDragStart(dragStartOffset, drawableList)

        // Perform drag
        val dragOffset = Offset(340f, 340f)
        manager.onDrag(dragOffset)

        // Calculate expected change
        val expectedChange = Offset(20f, 20f) // 340-320, 340-320

        // Verify drag offset updated
        assertEquals(dragOffset, manager.dragOffset)

        // Verify onDrag was called with correct change
        assertTrue(mockDraggable.onDragCalled)
        assertEquals(expectedChange, mockDraggable.lastDragAmount)

        // Verify drawable was translated
        assertEquals(expectedChange, mockDraggable.lastTranslateOffset)
    }

    @Test
    fun testOnDragEnd_shouldClearSelectionAndCallDragEndMethod() {
        // Setup: first select a drawable
        val dragStartOffset = Offset(320f, 320f)
        manager.onDragStart(dragStartOffset, drawableList)

        // End drag
        manager.onDragEnd()

        // Verify selection cleared
        assertTrue(manager.selectedDrawings.isEmpty())

        // Verify onDragEnd was called
        assertTrue(mockDraggable.onDragEndCalled)
        assertEquals(dragStartOffset, mockDraggable.lastDragEndOffset)
    }

    @Test
    fun testOnDragCancel_shouldClearSelectionAndCallDragCancelMethod() {
        // Setup: first select a drawable
        val dragStartOffset = Offset(320f, 320f)
        manager.onDragStart(dragStartOffset, drawableList)

        // Cancel drag
        manager.onDragCancel()

        // Verify selection cleared
        assertTrue(manager.selectedDrawings.isEmpty())

        // Verify onDragCancel was called
        assertTrue(mockDraggable.onDragCancelCalled)
    }

    // Mock classes for testing

    open inner class MockDrawable(position: Offset, val mockSize: Size) : Drawable {
        override var style: Style = Style(strokeColor = Color.Black)
        override val matrix: Matrix = Matrix()
        override var size: Size = mockSize
        override val path: Path = Path().apply {
            addRect(Rect(0f, 0f, mockSize.width, mockSize.height))
        }

        var lastTranslateOffset: Offset? = null

        init {
            // Position the drawable
            matrix.reset()
            matrix.translate(position.x, position.y)
        }

        override fun translateBy(offset: Offset) {
            lastTranslateOffset = offset
            super.translateBy(offset)
        }
    }

    inner class MockClickableDrawable(position: Offset, size: Size) : MockDrawable(position, size), Interactable.Clickable {
        var onClickCalled = false
        var lastClickOffset: Offset? = null

        override fun onClick(offset: Offset) {
            onClickCalled = true
            lastClickOffset = offset
        }
    }

    inner class MockDraggableDrawable(position: Offset, size: Size) : MockDrawable(position, size), Interactable.Draggable {
        var onDragStartCalled = false
        var onDragCalled = false
        var onDragEndCalled = false
        var onDragCancelCalled = false

        var lastDragStartOffset: Offset? = null
        var lastDragAmount: Offset? = null
        var lastDragEndOffset: Offset? = null

        override fun onDragStart(offset: Offset) {
            onDragStartCalled = true
            lastDragStartOffset = offset
        }

        override fun onDrag(dragAmount: Offset) {
            onDragCalled = true
            lastDragAmount = dragAmount
        }

        override fun onDragEnd(offset: Offset) {
            onDragEndCalled = true
            lastDragEndOffset = offset
        }

        override fun onDragCancel() {
            onDragCancelCalled = true
        }
    }
}
