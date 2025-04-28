package commonMain.operation

import commonMain.connections.Connection

data class Operation(
    val id: String,
    val type: OperationType,
    val inputs: List<Connection> = emptyList(),
    val outputs: List<Connection> = emptyList(),
) {
    fun operate(): Boolean {
        return when (type) {
            OperationType.NOT -> !inputs[0].signal
            OperationType.AND -> inputs.all { it.signal }
            OperationType.OR -> inputs.any { it.signal }
            OperationType.NAND -> !inputs.all { it.signal }
            OperationType.NOR -> !inputs.any { it.signal }
            OperationType.XOR -> inputs.count { it.signal } % 2 == 1
        }
    }
}
