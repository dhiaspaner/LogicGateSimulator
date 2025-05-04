package commonMain.operation


interface Receptor  {

    sealed interface SingleReceptor : Receptor {
        val receivedSignal: Boolean
        fun operate(): Boolean
    }

    sealed interface MultipleReceptor : Receptor {
        val receivedSignals: List<Boolean>
    }
}
