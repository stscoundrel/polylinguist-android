package io.github.stscoundrel.polylinguist.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class PolylinguistHTTPServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var polylinguistAPIService: PolylinguistHTTPService

    // Canned JSON response from the actual API.
    private val jsonPayload = "[{\"Name\":\"TypeScript\",\"Percentage\":39.507047805095645,\"Color\":\"#3178c6\"},{\"Name\":\"JavaScript\",\"Percentage\":23.969843738884155,\"Color\":\"#f1e05a\"},{\"Name\":\"Go\",\"Percentage\":8.973698833473373,\"Color\":\"#00ADD8\"},{\"Name\":\"Rust\",\"Percentage\":5.517572886283362,\"Color\":\"#dea584\"},{\"Name\":\"Python\",\"Percentage\":5.474465045540108,\"Color\":\"#3572A5\"},{\"Name\":\"C#\",\"Percentage\":4.8665714269233655,\"Color\":\"#178600\"},{\"Name\":\"PHP\",\"Percentage\":4.189343787914038,\"Color\":\"#4F5D95\"},{\"Name\":\"Kotlin\",\"Percentage\":2.755940787285302,\"Color\":\"#A97BFF\"},{\"Name\":\"Java\",\"Percentage\":2.083404634958113,\"Color\":\"#b07219\"},{\"Name\":\"Nim\",\"Percentage\":1.2294002395734593,\"Color\":\"#ffc200\"},{\"Name\":\"Scala\",\"Percentage\":0.789438771291947,\"Color\":\"#c22d40\"},{\"Name\":\"C/C++\",\"Percentage\":0.31821354339913593,\"Color\":\"#555555\"},{\"Name\":\"Dart\",\"Percentage\":0.16377903097727128,\"Color\":\"#00B4AB\"},{\"Name\":\"Dockerfile\",\"Percentage\":0.072525769528794,\"Color\":\"#384d54\"},{\"Name\":\"F#\",\"Percentage\":0.06748818956682581,\"Color\":\"#b845fc\"},{\"Name\":\"Shell\",\"Percentage\":0.021265509305102376,\"Color\":\"#89e051\"}]"

    @Before
    fun setup() {
        // Start a mock web server
        mockWebServer = MockWebServer()
        mockWebServer.start()

        // Create Retrofit instance identically to app code. Only difference is test url.
        val retrofit = Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(mockWebServer.url("/"))
            .build()

        // Create PolylinguistAPIService instance
        polylinguistAPIService = retrofit.create(PolylinguistHTTPService::class.java)
    }

    @After
    fun tearDown() {
        // Shutdown the mock web server
        mockWebServer.shutdown()
    }

    @Test
    fun fetchesJsonResponseAndParsesToModels() = runBlocking {
        mockWebServer.enqueue(MockResponse()
            .setResponseCode(200)
            .setBody(jsonPayload))

        val results = polylinguistAPIService.getStatistics()

        // Correct amount of results.
        assertEquals(16, results.size)

        // Sample stat is as expected.
        val expectedStat = NetworkStatistic(
            language = "TypeScript",
            percentage = 39.507047805095645,
            color = "#3178c6",
        )

        assertEquals(expectedStat, results.first())
    }
}