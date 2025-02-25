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

    fun provide(): List<Int>

    companion object {
        const val GAME_COUNT = 3
    }
}





