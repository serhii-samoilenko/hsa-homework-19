package com.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CountingSortTest {

    @Test
    fun `should sort a collection of integers with no duplicates`() {
        // given
        val input = listOf(5, 2, 7, 1, 9)
        // when
        val actual = input.countingSorted()
        // then
        assertEquals(listOf(1, 2, 5, 7, 9), actual)
    }

    @Test
    fun `should sort a collection of integers with duplicates`() {
        // given
        val input = listOf(5, 2, 7, 1, 9, 2, 5)
        // when
        val actual = input.countingSorted()
        // then
        assertEquals(listOf(1, 2, 2, 5, 5, 7, 9), actual)
    }

    @Test
    fun `should sort a collection of integers with negative numbers`() {
        // given
        val input = listOf(-5, 2, -7, 1, 9)
        // when
        val actual = input.countingSorted()
        // then
        assertEquals(listOf(-7, -5, 1, 2, 9), actual)
    }

    @Test
    fun `should sort a collection of integers with all elements equal`() {
        // given
        val input = listOf(3, 3, 3, 3)
        // when
        val actual = input.countingSorted()
        // then
        assertEquals(listOf(3, 3, 3, 3), actual)
    }

    @Test
    fun `should sort a collection of integers with empty collection`() {
        // given
        val input = emptyList<Int>()
        // when
        val actual = input.countingSorted()
        // then
        assertEquals(emptyList<Int>(), actual)
    }

    @Test
    fun `should sort a collection of integers with large range of values`() {
        // given
        val input = listOf(1000, 20000, 500, 1, 70000, 10, 3000)
        // when
        val actual = input.countingSorted()
        // then
        assertEquals(listOf(1, 10, 500, 1000, 3000, 20000, 70000), actual)
    }

    @Test
    fun `should sort a collection of integers with a single element`() {
        // given
        val input = listOf(5)
        // when
        val actual = input.countingSorted()
        // then
        assertEquals(listOf(5), actual)
    }
}
