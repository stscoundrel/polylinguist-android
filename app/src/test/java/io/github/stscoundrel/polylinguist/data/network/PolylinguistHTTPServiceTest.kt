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
    private val jsonPayload =
        "[{\"Name\":\"TypeScript\",\"Size\":1027363,\"Percentage\":39.24044057517542,\"Color\":\"#3178c6\"},{\"Name\":\"JavaScript\",\"Size\":623325,\"Percentage\":23.808086938619766,\"Color\":\"#f1e05a\"},{\"Name\":\"Go\",\"Size\":233471,\"Percentage\":8.91749547290177,\"Color\":\"#00ADD8\"},{\"Name\":\"Rust\",\"Size\":143482,\"Percentage\":5.48033839510214,\"Color\":\"#dea584\"},{\"Name\":\"Python\",\"Size\":142361,\"Percentage\":5.437521460985599,\"Color\":\"#3572A5\"},{\"Name\":\"C#\",\"Size\":126553,\"Percentage\":4.833730118867601,\"Color\":\"#178600\"},{\"Name\":\"PHP\",\"Size\":108942,\"Percentage\":4.16107264631952,\"Color\":\"#4F5D95\"},{\"Name\":\"Kotlin\",\"Size\":89221,\"Percentage\":3.4078230854700102,\"Color\":\"#A97BFF\"},{\"Name\":\"Java\",\"Size\":54178,\"Percentage\":2.06934509952359,\"Color\":\"#b07219\"},{\"Name\":\"Nim\",\"Size\":31970,\"Percentage\":1.2211038213254306,\"Color\":\"#ffc200\"},{\"Name\":\"Scala\",\"Size\":20529,\"Percentage\":0.7841113652796297,\"Color\":\"#c22d40\"},{\"Name\":\"C/C++\",\"Size\":8275,\"Percentage\":0.316066128291146,\"Color\":\"#555555\"},{\"Name\":\"Dart\",\"Size\":4259,\"Percentage\":0.1626737934008448,\"Color\":\"#00B4AB\"},{\"Name\":\"Dockerfile\",\"Size\":1886,\"Percentage\":0.07203634053862251,\"Color\":\"#384d54\"},{\"Name\":\"F#\",\"Size\":1755,\"Percentage\":0.06703275590948171,\"Color\":\"#b845fc\"},{\"Name\":\"Shell\",\"Size\":553,\"Percentage\":0.021122002289426435,\"Color\":\"#89e051\"}]"

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
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(jsonPayload)
        )

        val results = polylinguistAPIService.getStatistics()

        // Correct amount of results.
        assertEquals(16, results.size)

        // Sample stat is as expected.
        val expectedStat = NetworkStatistic(
            language = "TypeScript",
            percentage = 39.24044057517542,
            size = 1027363,
            color = "#3178c6",
        )

        assertEquals(expectedStat, results.first())
    }
}