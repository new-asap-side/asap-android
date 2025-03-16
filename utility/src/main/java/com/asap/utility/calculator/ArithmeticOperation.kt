package com.asap.utility.calculator


// 연산
data class BinaryExpression(val left: Int, val right: Int, val operator: String) {
    override fun toString(): String = "$left $operator $right = ?"
}

internal interface ExpressionGenerator {
    fun generate(): BinaryExpression
}

abstract class ArithmeticOperation {
    abstract val expression: BinaryExpression

    abstract val choice: List<Int>

    abstract fun isAnswer(input: Int): Boolean

    companion object {
        const val GAME_COUNT = 3
    }
}





