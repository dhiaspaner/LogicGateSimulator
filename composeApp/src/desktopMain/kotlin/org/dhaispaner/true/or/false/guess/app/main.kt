package org.dhaispaner.`true`.or.`false`.guess.app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import commonMain.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "TrueOrFalseGuess",
    ) {
        App()
    }
}
