package io.github.stscoundrel.polylinguist.data.inmemory

import com.google.gson.Gson
import java.time.LocalDate

const val STATISTICS_2024_03_17 =
    "[{\"Name\":\"TypeScript\",\"Size\":1027363,\"Percentage\":38.92583705498804,\"Color\":\"#3178c6\"},{\"Name\":\"JavaScript\",\"Size\":623325,\"Percentage\":23.617209673991006,\"Color\":\"#f1e05a\"},{\"Name\":\"Go\",\"Size\":233471,\"Percentage\":8.846000978295999,\"Color\":\"#00ADD8\"},{\"Name\":\"Rust\",\"Size\":143482,\"Percentage\":5.436400719437817,\"Color\":\"#dea584\"},{\"Name\":\"Python\",\"Size\":142361,\"Percentage\":5.393927062766668,\"Color\":\"#3572A5\"},{\"Name\":\"C#\",\"Size\":126553,\"Percentage\":4.794976514454873,\"Color\":\"#178600\"},{\"Name\":\"Kotlin\",\"Size\":110381,\"Percentage\":4.182234341675372,\"Color\":\"#A97BFF\"},{\"Name\":\"PHP\",\"Size\":108942,\"Percentage\":4.127711958134085,\"Color\":\"#4F5D95\"},{\"Name\":\"Java\",\"Size\":54178,\"Percentage\":2.052754479152103,\"Color\":\"#b07219\"},{\"Name\":\"Nim\",\"Size\":31970,\"Percentage\":1.2113138303092166,\"Color\":\"#ffc200\"},{\"Name\":\"Scala\",\"Size\":20529,\"Percentage\":0.777824886531683,\"Color\":\"#c22d40\"},{\"Name\":\"C/C++\",\"Size\":8275,\"Percentage\":0.31353212217105936,\"Color\":\"#555555\"},{\"Name\":\"Dart\",\"Size\":4259,\"Percentage\":0.1613695840877996,\"Color\":\"#00B4AB\"},{\"Name\":\"Dockerfile\",\"Size\":1886,\"Percentage\":0.07145880150025594,\"Color\":\"#384d54\"},{\"Name\":\"F#\",\"Size\":1755,\"Percentage\":0.06649533225501017,\"Color\":\"#b845fc\"},{\"Name\":\"Shell\",\"Size\":553,\"Percentage\":0.0209526602490146,\"Color\":\"#89e051\"}]"
private val STATISTICS_DATE = LocalDate.of(2024, 3, 17)

class InMemoryStatisticsProvider {
    fun getStatistics(): InMemoryStatistics {
        val statistics = jsonStringToStatistics(STATISTICS_2024_03_17)

        return InMemoryStatistics(
            date = STATISTICS_DATE,
            statistics = statistics
        )
    }

    private fun jsonStringToStatistics(jsonString: String): List<InMemoryStatistic> {
        return Gson().fromJson(jsonString, Array<InMemoryStatistic>::class.java).toList()
    }
}