package io.github.stscoundrel.polylinguist.data.network

import retrofit2.http.GET

interface PolylinguistHTTPService {
    @GET("json")
    suspend fun getStatistics(): List<NetworkStatistic>
}