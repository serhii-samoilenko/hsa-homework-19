package com.example

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class AaTreeTest {

    // add() method tests:

    @Test
    fun `should add a value to an empty tree`() {
        // given
        val tree = AaTree<Int>()
        // when
        tree.add(1)
        // then
        assertTrue(tree.contains(1))
        assertEquals(1, tree.size())
    }

    @Test
    fun `should add a value to a tree with one node`() {
        // given
        val tree = AaTree<Int>()
        tree.add(1)
        // when
        tree.add(2)
        // then
        assertTrue(tree.contains(1))
        assertTrue(tree.contains(2))
        assertEquals(2, tree.size())
    }

    @Test
    fun `should not add a value that already exists in the tree`() {
        // given
        val tree = AaTree<Int>()
        tree.add(1)
        // when
        tree.add(1)
        // then
        assertTrue(tree.contains(1))
        assertEquals(1, tree.size())
    }

    @Test
    fun `should add multiple values to the tree`() {
        // given
        val tree = AaTree<Int>()
        val values = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        // when
        values.forEach { tree.add(it) }
        // then
        values.forEach { assertTrue(tree.contains(it)) }
        assertEquals(values.size, tree.size())
    }

    @Test
    fun `should add 1000 values to the tree`() {
        // given
        val tree = AaTree<Int>()
        val values = (1..1000).toList()
        // when
        values.forEach { tree.add(it) }
        // then
        values.forEach { assertTrue(tree.contains(it)) }
        assertEquals(values.size, tree.size())
    }

    // remove() method tests:

    @Test
    fun `should remove a value from an empty tree`() {
        // given
        val tree = AaTree<Int>()
        // when
        tree.remove(1)
        // then
        assertFalse(tree.contains(1))
        assertEquals(0, tree.size())
    }

    @Test
    fun `should remove a value from a tree with one node`() {
        // given
        val tree = AaTree<Int>()
        tree.add(1)
        // when
        tree.remove(1)
        // then
        assertFalse(tree.contains(1))
        assertEquals(0, tree.size())
    }

    @Test
    fun `should not remove a value that does not exist in the tree`() {
        // given
        val tree = AaTree<Int>()
        tree.add(1)
        // when
        tree.remove(2)
        // then
        assertTrue(tree.contains(1))
        assertEquals(1, tree.size())
    }

    @Test
    fun `should remove multiple values from the tree`() {
        // given
        val tree = AaTree<Int>()
        val values = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        values.forEach { tree.add(it) }
        // when
        values.forEach { tree.remove(it) }
        // then
        values.forEach { assertFalse(tree.contains(it)) }
        assertEquals(0, tree.size())
    }

    @Test
    fun `should remove odd values from the tree`() {
        // given
        val tree = AaTree<Int>()
        val values = (1..50).toList()
        values.forEach { tree.add(it) }
        // when
        values.filter { it % 2 != 0 }.forEach { tree.remove(it) }
        // then
        values.filter { it % 2 == 0 }.forEach { assertTrue(tree.contains(it)) }
        values.filter { it % 2 != 0 }.forEach { assertFalse(tree.contains(it)) }
        assertEquals(values.size / 2, tree.size())
    }

    @Test
    fun `should remove even values from the tree`() {
        // given
        val tree = AaTree<Int>()
        val values = (1..50).toList()
        values.forEach { tree.add(it) }
        // when
        values.filter { it % 2 == 0 }.forEach { tree.remove(it) }
        // then
        values.filter { it % 2 == 0 }.forEach { assertFalse(tree.contains(it)) }
        values.filter { it % 2 != 0 }.forEach { assertTrue(tree.contains(it)) }
        assertEquals(values.size / 2, tree.size())
    }

    // contains() method tests:

    @Test
    fun `should not contain a value that was never added to the tree`() {
        // given
        val tree = AaTree<Int>()
        // when
        val actual = tree.contains(1)
        // then
        assertFalse(actual)
    }

    @Test
    fun `should not contain a value that was previously added to the tree but has been removed`() {
        // given
        val tree = AaTree<Int>()
        tree.add(1)
        tree.remove(1)
        // when
        val actual = tree.contains(1)
        // then
        assertFalse(actual)
    }

    @Test
    fun `should contain a value that was added to the tree, but not at the root level`() {
        // given
        val tree = AaTree<Int>()
        tree.add(1)
        tree.add(2)
        // when
        val actual = tree.contains(2)
        // then
        assertTrue(actual)
    }

    @Test
    fun `should contain a value that was added to the tree multiple times`() {
        // given
        val tree = AaTree<Int>()
        tree.add(1)
        tree.add(1)
        // when
        val actual = tree.contains(1)
        // then
        assertTrue(actual)
    }

    @Test
    fun `should contain a value that was added to the tree, removed, and then added again`() {
        // given
        val tree = AaTree<Int>()
        tree.add(1)
        tree.remove(1)
        tree.add(1)
        // when
        val actual = tree.contains(1)
        // then
        assertTrue(actual)
    }

    // size() method tests:

    @Test
    fun `should return 0 for an empty tree`() {
        // given
        val tree = AaTree<Int>()
        // when
        val actual = tree.size()
        // then
        assertEquals(0, actual)
    }

    @Test
    fun `should return 1 for a tree with one node`() {
        // given
        val tree = AaTree<Int>()
        tree.add(1)
        // when
        val actual = tree.size()
        // then
        assertEquals(1, actual)
    }

    @Test
    fun `should return the number of nodes in the tree`() {
        // given
        val tree = AaTree<Int>()
        val values = (1..50).toList()
        values.forEach { tree.add(it) }
        // when
        val actual = tree.size()
        // then
        assertEquals(values.size, actual)
    }

    @Test
    fun `should return the number of nodes in the tree after removing some of them`() {
        // given
        val tree = AaTree<Int>()
        val values = (1..50).toList()
        values.forEach { tree.add(it) }
        // when
        values.filter { it % 2 == 0 }.forEach { tree.remove(it) }
        // then
        assertEquals(values.size / 2, tree.size())
    }

    @Test
    fun `should not change the size of the tree when adding a value that already exists`() {
        // given
        val tree = AaTree<Int>()
        tree.add(1)
        // when
        tree.add(1)
        // then
        assertEquals(1, tree.size())
    }

    // Visualisation:

    @Test
    fun `should print tree visualization`() {
        // given
        val tree = AaTree<Int>()
        (1..55).forEach { tree.add(it) }
        // then
        AaTree.visualizeTree(tree)
    }
}
