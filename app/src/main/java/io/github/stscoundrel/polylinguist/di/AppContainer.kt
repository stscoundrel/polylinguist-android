package io.github.stscoundrel.polylinguist.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.github.stscoundrel.polylinguist.data.DefaultStatisticsRepository
import io.github.stscoundrel.polylinguist.data.database.AppDatabase
import io.github.stscoundrel.polylinguist.data.inmemory.InMemoryStatisticsProvider
import io.github.stscoundrel.polylinguist.data.network.NetworkStatisticsService
import io.github.stscoundrel.polylinguist.data.network.PolylinguistHTTPService
import io.github.stscoundrel.polylinguist.data.network.StatisticsService
import io.github.stscoundrel.polylinguist.domain.usecase.GetCurrentStatisticsUseCase
import io.github.stscoundrel.polylinguist.domain.usecase.GetLatestStatisticsUseCase
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    // TODO: debug only
    val getCurrentStatisticsUseCase: GetCurrentStatisticsUseCase
    val getLatestStatisticsUseCase: GetLatestStatisticsUseCase
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer(private val context: Context) : AppContainer {
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
    private val statisticsService: StatisticsService by lazy {
        NetworkStatisticsService(retrofitService)
    }

    private val inMemoryStatisticsProvider: InMemoryStatisticsProvider by lazy {
        InMemoryStatisticsProvider()
    }

    private val statisticsRepository: DefaultStatisticsRepository by lazy {
        DefaultStatisticsRepository(
            statisticsService,
            AppDatabase.getDatabase(context).statisticsDao(),
            inMemoryStatisticsProvider,

            )
    }

    override val getCurrentStatisticsUseCase: GetCurrentStatisticsUseCase by lazy {
        GetCurrentStatisticsUseCase(statisticsRepository)
    }

    override val getLatestStatisticsUseCase: GetLatestStatisticsUseCase by lazy {
        GetLatestStatisticsUseCase(statisticsRepository)
    }
}