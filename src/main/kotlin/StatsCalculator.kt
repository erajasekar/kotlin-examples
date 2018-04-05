package example

import com.google.common.math.Quantiles

sealed class Stats {
    object Avg : Stats()
    object Count : Stats()
    object Sum : Stats()

    class Quantile(val percentile: Int) : Stats()

    fun calculate(values: List<Int>): Double {
        return calculate(this, values)
    }

    fun calculate(stats: Stats, values: List<Int>): Double =

            when (stats) {

                is Stats.Sum -> values.sum().toDouble()

                is Stats.Count -> values.size.toDouble()

                is Stats.Avg -> calculate(Stats.Sum, values) / calculate(Stats.Count, values)

                is Stats.Quantile -> Quantiles.percentiles().index(stats.percentile).compute(values)
            }
}


fun main(args: Array<String>) {

    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    println(Stats.Sum.calculate(numbers))

    println(Stats.Avg.calculate(numbers))

    println(Stats.Quantile(70).calculate(numbers))
}