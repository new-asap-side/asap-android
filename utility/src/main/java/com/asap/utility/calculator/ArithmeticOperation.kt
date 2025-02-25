package com.asap.utility.calculator


// 연산
data class BinaryExpression(val left: Int, val right: Int, val operator: String) {
    override fun toString(): String = "$left $operator $right = ?"
}

internal abstract class ExpressionGenerator {
    protected abstract fun generate(): BinaryExpression
}

interface ArithmeticOperation {
    val expression: BinaryExpression

    val choice: List<Int>

    fun isAnswer(input: Int): Boolean

    companion object {
        const val GAME_COUNT = 3
    }
}





