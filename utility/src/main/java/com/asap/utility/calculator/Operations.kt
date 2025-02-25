package com.asap.utility.calculator

import kotlin.random.Random

// 덧셈
internal class Addition : ExpressionGenerator(), ArithmeticOperation {
    override val expression: BinaryExpression = generate()

    override fun provide(): List<Int> {
        return mutableListOf<Int>().apply {
            val random1 = Random.nextInt(4, 16)
            val random2 = Random.nextInt(4, 16)
            val answer = expression.left + expression.right

            add(answer)
            add(answer - random1)
            add(answer + random2)
        }.shuffled()
    }

    override fun generate(): BinaryExpression {
        return BinaryExpression(
            Random.nextInt(60, 100), Random.nextInt(2, 60), "+"
        )
    }
}

// 뺄셈
internal class Subtraction : ExpressionGenerator(), ArithmeticOperation {
    override val expression: BinaryExpression = generate()

    override fun provide(): List<Int> {
        return mutableListOf<Int>().apply {
            val random1 = Random.nextInt(4, 16)
            val random2 = Random.nextInt(4, 16)
            val answer = expression.left - expression.right

            add(answer)
            add(answer - random1)
            add(answer + random2)
        }.shuffled()
    }

    override fun generate(): BinaryExpression {
        return BinaryExpression(
            Random.nextInt(60, 100), Random.nextInt(2, 60), "-"
        )
    }
}

// 곱셈
internal class Multiplication : ExpressionGenerator(), ArithmeticOperation {
    override val expression: BinaryExpression
        get() = generate()

    override fun provide(): List<Int> {
        return mutableListOf<Int>().apply {
            val random1 = Random.nextInt(4, 32)
            val random2 = Random.nextInt(4, 16)
            val answer = expression.left * expression.right

            add(answer)
            add(answer - random1)
            add(answer + random2)
        }.shuffled()
    }

    override fun generate(): BinaryExpression {
        return BinaryExpression(
            Random.nextInt(10, 100), Random.nextInt(2, 10), "*"
        )
    }
}

private val operations: Array<ArithmeticOperation> =
    arrayOf(Addition(), Subtraction(), Multiplication())

fun selectOperation(): ArithmeticOperation = operations[Random.nextInt(0, 3)]