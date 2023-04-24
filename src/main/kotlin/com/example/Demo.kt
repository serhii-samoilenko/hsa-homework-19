package com.example

import com.example.util.Report

/**
 * Data Structures and Algorithms demo
 */
fun runDemo() {
    val r = Report("REPORT.md")
    r.h1("Data Structures and Algorithms report")
    r.h2("AA Tree Performance")
    r.text("Measuring the performance of the AA Tree implementation for different datasets.")

    benchmarkTree(
        datasetSizes = listOf(16000, 32000, 64000, 128000, 256000, 512000, 1024000, 2048000, 4096000, 8192000),
        iterations = 4,
        report = r,
    )

    r.h2("Counting Sort Performance")
    r.text("Measuring the performance of the Counting Sort implementation for different datasets.")

    benchmarkSort(
        datasetSizes = listOf(128000, 256000, 512000, 1024000, 2048000, 4096000, 8192000, 16384000, 32768000),
        spreadSizes = listOf(1_000, 100_000, 10_000_000, 1_000_000_000),
        iterations = 4,
        report = r,
    )

    r.writeToFile()
}
