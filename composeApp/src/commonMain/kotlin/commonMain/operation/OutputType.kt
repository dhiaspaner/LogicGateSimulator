package commonMain.operation

sealed interface OutputType {

    interface SingleSignal : OutputType {
        val outputSignal: Boolean
        fun operate(): Boolean
    }

    interface MultipleSignals : OutputType {
        val signals: List<Boolean>
        fun operate(): List<Boolean>
    }
}
