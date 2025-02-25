package com.asap.utility

import com.asap.utility.calculator.Addition
import com.asap.utility.calculator.Subtraction
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class OperationTest {
    @Test
    fun addition_isCorrect() {
        repeat(10) {
            Addition().also {
                it.provide().contains(it.expression.left + it.expression.right)
            }.let {
                println(it.expression)
                println(it.provide())
            }

        }
    }

    @Test
    fun subtraction_test() {
        repeat(10) {
            Subtraction().also {
                it.provide().contains(it.expression.left - it.expression.right)
            }.let {
                println(it.expression)
                println(it.provide())
            }
        }
    }
}