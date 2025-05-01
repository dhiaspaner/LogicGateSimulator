package commonMain.connections

import commonMain.operation.Receptor

data class Connection(
    val from: Receptor,
    val to: Receptor,
    val inputIndex: Int,
    var signal: Boolean = false
)
