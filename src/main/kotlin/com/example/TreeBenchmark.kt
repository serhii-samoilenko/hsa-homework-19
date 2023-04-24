package com.example

import com.example.util.Report
import kotlin.math.min
import kotlin.math.sqrt
import kotlin.system.measureTimeMillis

/**
 * Data Structures and Algorithms demo
 */
fun benchmarkTree(
    datasetSizes: List<Int>,
    iterations: Int,
    report: Report,
) {
    fun allSizes(producer: (size: Int) -> Sequence<Int>): Sequence<List<Int>> =
        datasetSizes.asSequence()
            // Allocate the dataset in advance to avoid measuring the time it takes to generate the dataset
            .map { producer(it).toList() }

    fun runTreePerformance(dataset: List<Int>, operation: (tree: AaTree<Int>, value: Int) -> Unit): Double {
        // Warm up
        with(AaTree<Int>()) { dataset.subList(0, min(64000, dataset.size)).forEach { value -> operation(this, value) } }
        // Repeat measurements
        val duration = (1..iterations).map { _ ->
            measureTimeMillis {
                val tree = AaTree<Int>()
                dataset.forEach { operation(tree, it) }
            }.toDouble()
        }.average()

        val kSize = dataset.size / 1000
        val oneKDuration = duration / kSize
        println("Count: $kSize K, Duration: $duration ms, 1K duration: $oneKDuration ms")
        return oneKDuration
    }

    val insertionResults = LinkedHashMap<String, LinkedHashMap<String, Double>>()
    val searchResults = LinkedHashMap<String, LinkedHashMap<String, Double>>()
    val deletionResults = LinkedHashMap<String, LinkedHashMap<String, Double>>()

    fun recordTreePerformance(
        dataset: List<Int>,
        datasetType: String,
    ) {
        val kSize = (dataset.size / 1000).toString()
        insertionResults.getOrPut(kSize) { LinkedHashMap() }[datasetType] = runTreePerformance(dataset) { tree, value -> tree.add(value) }
        searchResults.getOrPut(kSize) { LinkedHashMap() }[datasetType] = runTreePerformance(dataset) { tree, value -> tree.contains(value) }
        deletionResults.getOrPut(kSize) { LinkedHashMap() }[datasetType] = runTreePerformance(dataset) { tree, value -> tree.remove(value) }
    }

    println("Ascending datasets...")
    allSizes { ascendingDataset(it) }.forEach { dataset -> recordTreePerformance(dataset, "Ascending") }

    println("Descending datasets...")
    allSizes { descendingDataset(it) }.forEach { dataset -> recordTreePerformance(dataset, "Descending") }

    println("Random datasets...")
    allSizes { randomDataset(it) }.forEach { dataset -> recordTreePerformance(dataset, "Random") }

    println("Alternating datasets...")
    allSizes { alternatingDataset(it) }.forEach { dataset -> recordTreePerformance(dataset, "Alternating") }

    println("Repeating datasets...")
    allSizes { repeatingDataset(it, repetitions = it / 100, range = (1..sqrt(it.toDouble()).toInt())) }
        .forEach { dataset -> recordTreePerformance(dataset, "Repeating") }

    println("Left skewed datasets...")
    allSizes { leftSkewedDataset(it, range = (1..sqrt(it.toDouble()).toInt())) }
        .forEach { dataset -> recordTreePerformance(dataset, "Left-skewed") }

    println("Right skewed datasets...")
    allSizes { rightSkewedDataset(it, range = (1..sqrt(it.toDouble()).toInt())) }
        .forEach { dataset -> recordTreePerformance(dataset, "Right-skewed") }

    report.h3("Performance results for insertion operations")
    report.htmlTable("Insertion, x1000", insertionResults)

    report.h3("Performance results for search operations")
    report.htmlTable("Search, x1000", searchResults)

    report.h3("Performance results for deletion operations")
    report.htmlTable("Deletion, x1000", deletionResults)
}
