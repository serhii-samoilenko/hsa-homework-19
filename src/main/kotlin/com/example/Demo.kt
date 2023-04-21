package com.example

import com.example.util.Report

/**
 * Data Structures and Algorithms demo
 */
fun runDemo() {
    val r = Report("REPORT.md")
    r.h1("Data Structures and Algorithms report")

    r.writeToFile()
}
