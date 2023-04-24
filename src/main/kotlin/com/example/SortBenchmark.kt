package com.example

import com.example.util.Report
import kotlin.math.min
import kotlin.system.measureTimeMillis

/**
 * Data Structures and Algorithms demo
 */
fun benchmarkSort(
    datasetSizes: List<Int>,
    spreadSizes: List<Int>,
    iterations: Int,
    report: Report,
) {
    fun getSortDatasets(): Sequence<Sequence<List<Int>>> =
        datasetSizes.asSequence().map { size ->
            spreadSizes.asSequence().map { spread ->
                val max = spread / 2
                val min = -max
                randomDataset(size, min, max).toList()
            }
        }

    fun runSortPerformance(dataset: List<Int>): Double {
        // Repeat measurements
        val duration = (1..iterations).map { _ ->
            measureTimeMillis {
                dataset.countingSorted()
            }.toDouble()
        }.average()

        val kSize = dataset.size / 1000
        val kDuration = duration / 1000
        println("Count: $kSize K, Duration: $duration ms, 1K duration: $kDuration ms")
        return kDuration
    }

    val sortingResults = LinkedHashMap<String, LinkedHashMap<String, Double>>()

    fun recordSortPerformance(dataset: List<Int>) {
        val kSize = (dataset.size / 1000).toString() + "K"
        val spread = (dataset.max() - dataset.min()).toString()
        sortingResults.getOrPut(kSize) { LinkedHashMap() }[spread] = runSortPerformance(dataset)
    }

    // Warm up
    randomDataset(size = 64000, min = 1, max = 1_000_000).toList().countingSorted()

    getSortDatasets().forEach { datasets ->
        datasets.forEach { dataset ->
            recordSortPerformance(dataset)
        }
    }

    report.h3("Performance results for sorting operations")
    report.htmlTable("Counting sort, x1000", sortingResults)
}

fun constraintSize(value: Long) {
    if (value > Int.MAX_VALUE) throw IllegalArgumentException("The dataset size is too large")
}
