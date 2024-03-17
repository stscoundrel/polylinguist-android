package io.github.stscoundrel.polylinguist.data.inmemory

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class InMemoryStatistics(
    val date: LocalDate,
    val statistics: List<InMemoryStatistic>
)

// Note: while fields do match the NetWorkStatistic,
// the serialization & its annotations are different.
data class InMemoryStatistic(
    @SerializedName(value = "Name")
    val language: String,

    @SerializedName(value = "Size")
    val size: Int,

    @SerializedName(value = "Percentage")
    val percentage: Double,

    @SerializedName(value = "Color")
    val color: String,
)