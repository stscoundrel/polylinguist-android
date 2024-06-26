package io.github.stscoundrel.polylinguist.ui.utils

import junit.framework.TestCase
import org.junit.Test

class PercentagesTest {
    @Test
    fun formatPresentationPercentagesTest() {
        val testCases = listOf(
            0.01 to "0.01%",
            66.666666666666 to "66.67%",
            12.4999999999 to "12.50%",
        )

        for ((value, expected) in testCases) {
            val result = formatPresentationPercentage(value)
            TestCase.assertEquals(expected, result)
        }
    }

    @Test
    fun getPercentagePrefixTest() {
        val testCases = listOf(
            0.00 to "+",
            -66.666666666666 to "-",
            12.4999999999 to "+",
        )

        for ((value, expected) in testCases) {
            val result = getPercentPrefix(value)
            TestCase.assertEquals(expected, result)
        }
    }
}