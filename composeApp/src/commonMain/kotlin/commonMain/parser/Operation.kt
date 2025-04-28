package commonMain.parser

import commonMain.operation.Operation

data class Calculation(
    val operator: Operation,
    val nextOperator: Operation? = null
)

