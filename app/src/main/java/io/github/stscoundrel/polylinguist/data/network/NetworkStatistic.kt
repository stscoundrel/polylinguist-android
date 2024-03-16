package io.github.stscoundrel.polylinguist.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkStatistic(
    @SerialName(value = "Name")
    val language: String,

    @SerialName(value = "Size")
    val size: Int,

    @SerialName(value = "Percentage")
    val percentage: Double,

    @SerialName(value = "Color")
    val color: String,
)