package com.example

import com.example.util.Report
import kotlin.math.min
import kotlin.math.sqrt
import kotlin.system.measureTimeMillis

const val INITIAL_DATASET_SIZE = 16000
const val DATASET_GROWTH_FACTOR = 2
const val DATASET_GROWTH_STEPS = 11
const val MEASUREMENT_ITERATIONS = 4

/**
 * Data Structures and Algorithms demo
 */
fun runDemo() {
    val r = Report("REPORT.md")
    r.h1("Data Structures and Algorithms report")
    r.h2("AA Tree Performance")
    r.text("Measuring the performance of the AA Tree implementation for different datasets.")

    val insertionResults = LinkedHashMap<String, LinkedHashMap<String, Result>>()
    val searchResults = LinkedHashMap<String, LinkedHashMap<String, Result>>()
    val deletionResults = LinkedHashMap<String, LinkedHashMap<String, Result>>()

    fun recordPerformance(
        dataset: List<Int>,
        datasetType: String,
    ) {
        val kSize = (dataset.size / 1000).toString()
        insertionResults.getOrPut(kSize) { LinkedHashMap() }[datasetType] = measurePerformance(dataset) { tree, value -> tree.add(value) }
        searchResults.getOrPut(kSize) { LinkedHashMap() }[datasetType] = measurePerformance(dataset) { tree, value -> tree.contains(value) }
        deletionResults.getOrPut(kSize) { LinkedHashMap() }[datasetType] = measurePerformance(dataset) { tree, value -> tree.remove(value) }
    }

    println("Ascending datasets...")
    allSizes { ascendingDataset(it) }.forEach { dataset -> recordPerformance(dataset, "Ascending") }

    println("Descending datasets...")
    allSizes { descendingDataset(it) }.forEach { dataset -> recordPerformance(dataset, "Descending") }

    println("Random datasets...")
    allSizes { randomDataset(it) }.forEach { dataset -> recordPerformance(dataset, "Random") }

    println("Alternating datasets...")
    allSizes { alternatingDataset(it) }.forEach { dataset -> recordPerformance(dataset, "Alternating") }

    println("Repeating datasets...")
    allSizes { repeatingDataset(it, repetitions = it / 100, range = (1..sqrt(it.toDouble()).toInt())) }
        .forEach { dataset -> recordPerformance(dataset, "Repeating") }

    println("Left skewed datasets...")
    allSizes { leftSkewedDataset(it, range = (1..sqrt(it.toDouble()).toInt())) }
        .forEach { dataset -> recordPerformance(dataset, "Left-skewed") }

    println("Right skewed datasets...")
    allSizes { rightSkewedDataset(it, range = (1..sqrt(it.toDouble()).toInt())) }
        .forEach { dataset -> recordPerformance(dataset, "Right-skewed") }

    r.h3("Performance results for insertion operations")
    r.htmlTable("Insertion, x1000", insertionResults)

    r.h3("Performance results for search operations")
    r.htmlTable("Search, x1000", searchResults)

    r.h3("Performance results for deletion operations")
    r.htmlTable("Deletion, x1000", deletionResults)

    r.writeToFile()
}

fun allSizes(producer: (size: Int) -> Sequence<Int>): Sequence<List<Int>> {
    return getDatasets(INITIAL_DATASET_SIZE, DATASET_GROWTH_STEPS, DATASET_GROWTH_FACTOR, producer)
}

fun getDatasets(initialSize: Int, count: Int, growthFactor: Int, producer: (size: Int) -> Sequence<Int>): Sequence<List<Int>> =
    generateSequence(initialSize.toLong()) { it * growthFactor.toLong() }
        .onEach { if (it > Int.MAX_VALUE) throw IllegalArgumentException("The dataset size is too large") }
        .take(count)
        // Allocate the dataset in advance to avoid measuring the time it takes to generate the dataset
        .map { producer(it.toInt()).toList() }

fun measurePerformance(dataset: List<Int>, operation: (tree: AaTree<Int>, value: Int) -> Unit): Result {
    // Warm up
    with(AaTree<Int>()) { dataset.subList(0, min(64000, dataset.size)).forEach { value -> operation(this, value) } }
    // Repeat measurements
    val duration = (1..MEASUREMENT_ITERATIONS).map { _ ->
        measureTimeMillis {
            val tree = AaTree<Int>()
            dataset.forEach { operation(tree, it) }
        }.toDouble()
    }.average()

    val kSize = dataset.size / 1000
    val kDuration = duration / kSize
    println("Count: $kSize K, Duration: $duration ms, 1K duration: $kDuration ms")
    return Result(kSize, kDuration)
}

data class Result(
    val count: Int,
    val duration: Double,
)
