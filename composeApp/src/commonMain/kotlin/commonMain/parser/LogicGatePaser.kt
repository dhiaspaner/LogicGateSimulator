package commonMain.parser


enum class Operand {
    True,
    False;

    val value get() = this.toString().uppercase()

    fun toBoolean() = this == True


    operator fun not() : Operand {
        return if (this == True) {
            False
        } else {
            True
        }
    }

}


fun findOperandIndex(input: String, startIndex: Int = 0): Int {
    val indexOfTrue = input.indexOf(Operand.True.toString().uppercase(), startIndex)
    val indexOfFalse = input.indexOf(Operand.False.toString().uppercase(), startIndex)


    return if (indexOfTrue == -1) {
        indexOfFalse
    } else if (indexOfFalse == -1) {
        indexOfTrue
    } else {
        minOf(indexOfTrue, indexOfFalse)
    }

}
