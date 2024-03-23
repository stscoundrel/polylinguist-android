package io.github.stscoundrel.polylinguist.ui.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun formatPresentationDate(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("d.M.yyyy")
    return date.format(formatter)
}