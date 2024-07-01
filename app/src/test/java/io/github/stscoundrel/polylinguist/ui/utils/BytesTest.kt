package io.github.stscoundrel.polylinguist.ui.utils

import junit.framework.TestCase.assertEquals
import org.junit.Test

class BytesTest {
    @Test
    fun testBytesFormatting() {
        val testCases = listOf(
            Pair(500, "500 bytes"),
            Pair(1024, "1.00 KB"),
            Pair(2000, "1.95 KB"),
            Pair(1024 * 1024, "1.000 MB"),
            Pair(5_000_000, "4.768 MB"),
        )

        for ((value, expected) in testCases) {
            assertEquals(expected, formatBytes(value))
        }
    }

    @Test
    fun testBytesFormattingWithComparison() {
        val testCases = listOf(
            Triple(500, 400, "500 bytes (+25.00%)"),
            Triple(200, 100, "200 bytes (+100.00%)"),
            Triple(5_000_000, 4_666_666, "4.768 MB (+7.14%)"),
            Triple(100, 300, "100 bytes (-66.67%)"),
        )

        for ((value, comparisonValue, expected) in testCases) {
            assertEquals(expected, formatBytes(value, comparisonValue))
        }
    }
}