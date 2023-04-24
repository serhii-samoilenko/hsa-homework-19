package com.example

import kotlin.math.min
import kotlin.math.pow

/**
 * Generates a lazily evaluated sequence of integers in ascending order from 1 to the specified size.
 *
 * @param size the number of integers to generate.
 * @return a Sequence<Int> object that generates integers from 1 to size in ascending order.
 */
fun ascendingDataset(size: Int): Sequence<Int> = sequence {
    for (i in 1..size) yield(i)
}

/**
 * Generates a lazily evaluated sequence of integers in descending order from the specified size to 1.
 *
 * @param size the number of integers to generate.
 * @return a Sequence<Int> object that generates integers from size to 1 in descending order.
 */
fun descendingDataset(size: Int): Sequence<Int> = sequence {
    for (i in size downTo 1) yield(i)
}

/**
 * Generates a lazily evaluated sequence of random integers within a specified range.
 * The resulting dataset is guaranteed to contain the minimum and maximum values.
 *
 * @param size the number of integers to generate.
 * @param min the minimum value of the integers to generate (inclusive).
 * @param max the maximum value of the integers to generate (inclusive).
 * @return a Sequence<Int> object that generates size random integers within the specified range.
 */
fun randomDataset(size: Int, min: Int = 1, max: Int = Int.MAX_VALUE): Sequence<Int> = sequence {
    yield(min)
    for (i in 1..size - 2) yield((min..max).random())
    yield(max)
}

/**
 * Generates an alternating dataset consisting of low and high values from a specified range.
 *
 * The function creates a sequence of integers where high values increase from the middle point
 * of the range towards the maximum value, while low values increase from the minimum value
 * (1 in this case) towards the middle point.
 *
 * @param size The number of elements in the dataset. The size should be a positive integer.
 * @return A sequence of integers representing the alternating dataset.
 */
fun alternatingDataset(size: Int): Sequence<Int> = sequence {
    val halfSize = (size + 1) / 2
    val maxValue = min(halfSize, size - halfSize)
    for (i in 1..maxValue) {
        yield(i)
        if (i + maxValue <= size) {
            yield(i + maxValue)
        }
    }
}

/**
 * Generates a dataset with repeated values.
 *
 * The function creates a sequence of integers with a specified number of unique values,
 * where each unique value is repeated a specified number of times. The unique values are
 * randomly chosen from a specified range of integers.
 *
 * @param size The number of unique values in the dataset. The size should be a positive integer.
 * @param repetitions The number of times each unique value should be repeated. The repetitions should be a positive integer.
 * @param range The range of integers to choose the unique values from (default: 1..10).
 * @return A sequence of integers representing the dataset with repeated values.
 */
fun repeatingDataset(size: Int, repetitions: Int, range: IntRange = 1..10): Sequence<Int> = sequence {
    for (i in 1..size / repetitions) {
        val value = range.random()
        repeat(repetitions) {
            yield(value)
        }
    }
}

/**
 * Generates a left-skewed dataset using a custom probability function.
 *
 * The function creates a sequence of integers with a specified size, where the values are
 * distributed according to a left-skewed distribution within a specified range of integers.
 *
 * @param size The number of elements in the dataset. The size should be a positive integer.
 * @param range The range of integers for the dataset (default: 1..100).
 * @return A sequence of integers representing the left-skewed dataset.
 */
fun leftSkewedDataset(size: Int, range: IntRange = 1..100): Sequence<Int> = sequence {
    val skewness = 2.0 // higher values = more skewed
    val maxValue = range.last.toDouble()
    for (i in 1..size) {
        val probability = Math.random().pow(skewness)
        val value = (range.first + (maxValue * probability).toInt()).coerceIn(range)
        yield(value)
    }
}

/**
 * Generates a right-skewed dataset using a custom probability function.
 *
 * The function creates a sequence of integers with a specified size, where the values are
 * distributed according to a right-skewed distribution within a specified range of integers.
 *
 * @param size The number of elements in the dataset. The size should be a positive integer.
 * @param range The range of integers for the dataset (default: 1..100).
 * @return A sequence of integers representing the right-skewed dataset.
 */
fun rightSkewedDataset(size: Int, range: IntRange = 1..100): Sequence<Int> = sequence {
    val skewness = 2.0 // higher values = more skewed
    val maxValue = range.last.toDouble()
    for (i in 1..size) {
        val probability = 1 - Math.random().pow(skewness)
        val value = (range.first + (maxValue * probability).toInt()).coerceIn(range)
        yield(value)
    }
}
