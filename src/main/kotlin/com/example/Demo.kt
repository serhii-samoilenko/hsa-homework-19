package com.example

import com.example.util.Report
import kotlin.math.sqrt
import kotlin.system.measureTimeMillis

/**
 * Data Structures and Algorithms demo
 */
fun runDemo() {
    val r = Report("REPORT.md")
    r.h1("Data Structures and Algorithms report")
    r.h2("AA Tree Performance")
    r.text("Measuring the performance of the AA Tree implementation for different datasets.")

    r.h3("Adding values")
    r.text("Measuring the performance of adding values to the AA Tree for different datasets.")
    val addResults = LinkedHashMap<String, List<Result>>()

    r.text("Ascending datasets...")
    defaultDatasets { ascendingDataset(it) }.map { data ->
        measurePerformance(data) { tree, value -> tree.add(value) }
    }.toList().also { addResults["Ascending datasets"] = it }

    r.text("Descending datasets...")
    defaultDatasets { descendingDataset(it) }.map { data ->
        measurePerformance(data) { tree, value -> tree.add(value) }
    }.toList().also { addResults["Descending datasets"] = it }

    r.text("Random datasets...")
    defaultDatasets { randomDataset(it) }.map { data ->
        measurePerformance(data) { tree, value -> tree.add(value) }
    }.toList().also { addResults["Random datasets"] = it }

    r.text("Alternating datasets...")
    defaultDatasets { alternatingDataset(it) }.map { data ->
        measurePerformance(data) { tree, value -> tree.add(value) }
    }.toList().also { addResults["Alternating datasets"] = it }

    r.text("Repeating datasets...")
    defaultDatasets { repeatingDataset(it, repetitions = it / 100, range = (1..sqrt(it.toDouble()).toInt())) }.map { data ->
        measurePerformance(data) { tree, value -> tree.add(value) }
    }.toList().also { addResults["Repeating datasets"] = it }

    r.text("Left skewed datasets...")
    defaultDatasets { leftSkewedDataset(it, range = (1..sqrt(it.toDouble()).toInt())) }.map { data ->
        measurePerformance(data) { tree, value -> tree.add(value) }
    }.toList().also { addResults["Left skewed datasets"] = it }

    r.text("Right skewed datasets...")
    defaultDatasets { rightSkewedDataset(it, range = (1..sqrt(it.toDouble()).toInt())) }.map { data ->
        measurePerformance(data) { tree, value -> tree.add(value) }
    }.toList().also { addResults["Right skewed datasets"] = it }

    addResults.forEach { (name, results) ->
        r.h4(name)
        results.forEach {
            r.text("Count: ${it.count}, Duration: ${it.duration}ms")
        }
    }

    r.writeToFile()
}

fun defaultDatasets(producer: (size: Int) -> Sequence<Int>) = getDatasets(1000, 18, 2, producer)

fun getDatasets(initialSize: Int, count: Int, growthFactor: Int, producer: (size: Int) -> Sequence<Int>): Sequence<Sequence<Int>> =
    generateSequence(initialSize.toLong()) { it * growthFactor.toLong() }
        .onEach { if (it > Int.MAX_VALUE) throw IllegalArgumentException("The dataset size is too large") }
        .take(count)
        .map { producer(it.toInt()) }

fun measurePerformance(dataset: Sequence<Int>, operation: (tree: AaTree<Int>, value: Int) -> Unit): Result {
    var count = 0L
    val tree = AaTree<Int>()
    val duration = measureTimeMillis {
        dataset.forEach {
            operation(tree, it)
            count++
        }
    }
    return Result(count, duration)
}

data class Result(
    val count: Long,
    val duration: Long,
)
