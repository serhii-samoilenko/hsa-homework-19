package com.example

/**
 * Sorts a collection of integers using the counting sort algorithm.
 * @see countingSort
 */
fun Collection<Int>.countingSorted(): List<Int> {
    return countingSort(this)
}

/**
 * Sorts a collection of integers using the counting sort algorithm.
 * The counting sort algorithm works by counting the number of occurrences of each value in the input collection,
 * and then generating the output list by iterating over the range of values from the minimum to the maximum value
 * in the input collection, and adding the corresponding number of occurrences of each value to the output list.
 *
 * If the input collection is empty, the function returns an empty list.
 *
 * @param input the input collection of integers to be sorted.
 * @return the sorted list of integers.
 */
fun countingSort(input: Collection<Int>): List<Int> {
    val first = input.firstOrNull() ?: return emptyList()
    val (min, max) = input.fold(first to first) { (currMin, currMax), value ->
        minOf(currMin, value) to maxOf(currMax, value)
    }
    val counts = IntArray(max - min + 1)
    for (value in input) {
        counts[value - min]++
    }
    val output = mutableListOf<Int>()
    for (i in counts.indices) {
        for (j in 0 until counts[i]) {
            output.add(i + min)
        }
    }
    return output
}
