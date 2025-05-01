package commonMain

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import commonMain.operation.AndOperation
import commonMain.operation.ConstantVoltage
import commonMain.operation.NotOperation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val fontResolver = LocalFontFamilyResolver.current
        val density = LocalDensity.current

        val lowConstantVoltage = remember {
            ConstantVoltage(
                type = ConstantVoltage.Type.HIGH,
                density = density,
                fontFamilyResolver = fontResolver
            ).apply {
                matrix.translate(x = 50f, y = 50f)
            }
        }

        val highConstantVoltage = remember {
            ConstantVoltage(
                type = ConstantVoltage.Type.HIGH,
                density = density,
                fontFamilyResolver = fontResolver
            ).apply {
                matrix.translate(x = 50f, y = 150f)
            }
        }

        val notOperator = remember {
            NotOperation(
                receivedSignal = false
            ).apply {
                matrix.translate(x = 200f, y = 200f)
            }
        }

        val andOperation = remember {
            AndOperation(
                receivedSignals = listOf(false, false)
            ).apply {
                matrix.translate(x = 100f, y = 100f)
            }
        }

        val graphics = remember {
            listOf(highConstantVoltage, lowConstantVoltage, notOperator, andOperation)
        }

        // Create a drag and drop manager
        val dragAndDropManager = rememberDragAndDropManager()

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .draggable(
                    dragAndDropManager = dragAndDropManager,
                    onDragStart = { offset ->
                        // Find the drawable at the clicked position and start dragging it
                        findDrawableAt(graphics, offset)?.let { drawable ->
                            dragAndDropManager.startDrag(drawable, offset)
                        }.also {
                            println("drawable: $it")
                        }
                    }
                )
        ) {
            graphics.forEach { drawable ->
                drawable.draw(this)
            }
        }
    }
}
