package com.asap.utility.calculator

import kotlin.random.Random

// 덧셈
internal class Addition : ArithmeticOperation(), ExpressionGenerator {
    override val expression: BinaryExpression = generate()

    override val choice: List<Int> = mutableListOf<Int>().apply {
        val random1 = Random.nextInt(4, 16)
        val random2 = Random.nextInt(4, 16)
        val answer = expression.left + expression.right

        add(answer)
        add(answer - random1)
        add(answer + random2)
    }.shuffled()

    override fun isAnswer(input: Int): Boolean = input == expression.left + expression.right

    override fun generate(): BinaryExpression {
        return BinaryExpression(
            Random.nextInt(60, 100), Random.nextInt(2, 60), "+"
        )
    }
}

// 뺄셈
internal class Subtraction : ArithmeticOperation(), ExpressionGenerator {
    override val expression: BinaryExpression = generate()

    override val choice: List<Int> = mutableListOf<Int>().apply {
        val random1 = Random.nextInt(4, 16)
        val random2 = Random.nextInt(4, 16)
        val answer = expression.left - expression.right

        add(answer)
        add(answer - random1)
        add(answer + random2)
    }.shuffled()

    override fun isAnswer(input: Int): Boolean = input == expression.left - expression.right

    override fun generate(): BinaryExpression {
        return BinaryExpression(
            Random.nextInt(60, 100), Random.nextInt(2, 60), "-"
        )
    }
}

// 곱셈
internal class Multiplication : ArithmeticOperation(), ExpressionGenerator {
    override val expression: BinaryExpression = generate()

    override val choice: List<Int> = mutableListOf<Int>().apply {
        val random1 = Random.nextInt(4, 32)
        val random2 = Random.nextInt(4, 16)
        val answer = expression.left * expression.right

        add(answer)
        add(answer - random1)
        add(answer + random2)
    }.shuffled()

    override fun isAnswer(input: Int): Boolean = input == expression.left * expression.right

    override fun generate(): BinaryExpression {
        return BinaryExpression(
            Random.nextInt(10, 100), Random.nextInt(2, 10), "x"
        )
    }
}

fun randomOperation(): ArithmeticOperation {
    val operations = arrayOf(Addition(), Subtraction(), Multiplication())
    return operations[Random.nextInt(0, 3)]
}