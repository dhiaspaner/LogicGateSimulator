package commonMain.connections

import commonMain.operation.Operation

data class Connection(
    val from: Operation,
    val to: Operation,
    val inputIndex: Int,
    var signal: Boolean = false
)
