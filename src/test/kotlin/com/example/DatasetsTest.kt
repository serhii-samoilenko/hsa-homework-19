package com.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class DatasetsTest {

    @Test
    fun `should generate ascending dataset`() {
        assertEquals(listOf(1, 2, 3, 4, 5), ascendingDataset(5).toList())
    }

    @Test
    fun `should generate descending dataset`() {
        assertEquals(listOf(5, 4, 3, 2, 1), descendingDataset(5).toList())
    }

    @Test
    fun `should generate random dataset`() {
        assertTrue(randomDataset(5, 1, 10).all { it in 1..10 })
    }

    @Test
    fun `should generate alternating dataset`() {
        assertEquals(listOf(1, 4, 2, 5, 3, 6), alternatingDataset(6).toList())
    }

    @Test
    fun `should generate repeating dataset`() {
        // when
        val actual = repeatingDataset(5, 3, 1..10).toList()
        // then
        assertTrue(actual.all { it in 1..10 })
        assertEquals(15, actual.size)
    }

    @Test
    fun `should generate a left-skewed dataset`() {
        // when
        val actual = leftSkewedDataset(1000, 1..100).toList()
        // then
        assertTrue(actual.all { it in 1..100 })
        assertEquals(1000, actual.size)
        val mean = actual.average()
        val median = actual.sorted().let { (it[actual.size / 2 - 1] + it[actual.size / 2]) / 2.0 }
        assertTrue(median < mean)
    }

    @Test
    fun `should generate a right-skewed dataset`() {
        // when
        val actual = rightSkewedDataset(1000, 1..100).toList()
        // then
        assertTrue(actual.all { it in 1..100 })
        assertEquals(1000, actual.size)
        val mean = actual.average()
        val median = actual.sorted().let { (it[actual.size / 2 - 1] + it[actual.size / 2]) / 2.0 }
        assertTrue(median > mean)
    }
}
