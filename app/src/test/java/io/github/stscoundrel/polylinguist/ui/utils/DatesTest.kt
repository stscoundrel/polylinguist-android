package io.github.stscoundrel.polylinguist.ui.utils

import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.LocalDate

class DatesTest {

    @Test
    fun formatPresentationDatesTest() {
        val testCases = listOf(
            LocalDate.of(2024, 1, 1) to "1.1.2024",
            LocalDate.of(2024, 3, 23) to "23.3.2024",
            LocalDate.of(2024, 12, 31) to "31.12.2024",
        )

        for ((date, expected) in testCases) {
            val result = formatPresentationDate(date)
            assertEquals(expected, result)
        }
    }
}