package io.github.stscoundrel.polylinguist.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.github.stscoundrel.polylinguist.data.network.NetworkStatisticsService
import io.github.stscoundrel.polylinguist.data.network.PolylinguistHTTPService
import io.github.stscoundrel.polylinguist.data.network.StatisticsService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    // TODO: debug only
    val statisticsService: StatisticsService
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://polylinguist.vercel.app/api/username/stscoundrel/"

    /**
     * HTTP client using retrofit.
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    /**
     * Polylinguist HTTP service.
     */
    private val retrofitService: PolylinguistHTTPService by lazy {
        retrofit.create(PolylinguistHTTPService::class.java)
    }

    /**
     * DI implementation for StatisticsService
     */
    override val statisticsService: StatisticsService by lazy {
        NetworkStatisticsService(retrofitService)
    }
}