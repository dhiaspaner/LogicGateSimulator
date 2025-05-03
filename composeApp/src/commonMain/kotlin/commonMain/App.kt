package commonMain

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp
import commonMain.interactions.clickable
import commonMain.interactions.draggable
import commonMain.interactions.rememberDrawableInteractionManager
import commonMain.operation.AndOperation
import commonMain.operation.ConstantVoltage
import commonMain.operation.NotOperation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {

        val textMeasurer = rememberTextMeasurer()



        val density = LocalDensity.current



        val lowConstantVoltage = remember {
            ConstantVoltage(
                type = ConstantVoltage.Type.LOW,
            ).apply {
                matrix.translate(x = 50f, y = 50f)
            }
        }

        val highConstantVoltage = remember {
            ConstantVoltage(
                type = ConstantVoltage.Type.HIGH,
            ).apply {
                matrix.translate(x = 50f, y = 150f)
            }
        }


        lowConstantVoltage.rememberMeasureText(textMeasurer,density)
        highConstantVoltage.rememberMeasureText(textMeasurer,density)



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
            listOf(
                highConstantVoltage,
                lowConstantVoltage,
                notOperator,
                andOperation
            )
        }

        val drawableInteractionManager = rememberDrawableInteractionManager()

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .draggable(
                    drawableList = graphics,
                    drawableInteractionManager = drawableInteractionManager
                )
                .clickable(
                    drawableList = graphics,
                    drawableInteractionManager = drawableInteractionManager,
                )
        ) {
            drawableInteractionManager.updateUi

            graphics.forEach { drawable ->
                drawable.draw(this)

                drawable.drawBounds(this)
            }

//            drawContext.canvas.save()
//            drawRect(
//                color = Color.Red,
//                topLeft = drawableInteractionManager.hoverOffset,
//                size = Size(50f, 50f),
//                alpha = 0.5f
//            )
//            this.drawContext.canvas.restore()



        }
    }
}

@Composable
fun DraggableTextCanvas() {
    var textOffset by remember { mutableStateOf(Offset(100f, 100f)) }
    var dragOffset by remember { mutableStateOf(Offset.Zero) }

    val textMeasurer = rememberTextMeasurer()

    // Measure the text once
    val textLayoutResult = textMeasurer.measure(
        text = AnnotatedString("Drag me!"),
        style = TextStyle(fontSize = 32.sp, color = Color.Black)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        dragOffset = it - textOffset
                    },
                    onDrag = { change, _ ->
                        textOffset = change.position - dragOffset
                    }
                )
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawText(
                textLayoutResult,
                topLeft = textOffset
            )
        }
    }
}
