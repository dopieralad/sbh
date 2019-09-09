package pl.dopierala.bio.sbh.solution.graph

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.util.function.Supplier

internal class DirectedMultigraphTest {

    @Test
    fun `Creates a new directed multigraph`() {
        assertDoesNotThrow {
            DirectedMultigraph<String, Int>()
        }
    }

    @Test
    fun `Gets an empty set of edges between nodes`() {
        // Given
        val graph = DirectedMultigraph<String, Int>()

        // Then
        assertAll(
                { assertEquals(emptySet<Int>(), graph.get("A" to "B")) }
        )
    }

    @Test
    fun `Adds a directed edge between nodes`() {
        // Given
        val graph = DirectedMultigraph<String, Int>()

        // When
        graph.add("A" to "B", 1)

        // Then
        assertAll(
                { assertEquals(setOf(1), graph.get("A" to "B")) }
        )
    }

    @Test
    fun `Adds a duplicate directed edge between nodes`() {
        // Given
        val graph = DirectedMultigraph<String, Int>()

        // When
        graph.add("A" to "B", 1)
        graph.add("A" to "B", 1)

        // Then
        assertAll(
                { assertEquals(setOf(1), graph.get("A" to "B")) }
        )
    }

    @Test
    fun `Adds multiple directed edges between nodes`() {
        // Given
        val graph = DirectedMultigraph<String, Int>()

        // When
        graph.add("A" to "B", 1)
        graph.add("A" to "B", 2)
        graph.add("B" to "A", 3)
        graph.add("B" to "A", 4)
        graph.add("C" to "A", 5)

        // Then
        assertAll(
                { assertEquals(setOf(1, 2), graph.get("A" to "B")) },
                { assertEquals(setOf(3, 4), graph.get("B" to "A")) },
                { assertEquals(setOf(5), graph.get("C" to "A")) }
        )
    }

    @Test
    fun `Equals to an empty graph when empty`() {
        // Given
        val graphOne = DirectedMultigraph<String, Int>()
        val graphTwo = DirectedMultigraph<String, Int>()

        // When
        val areEqual = graphOne == graphTwo

        // Then
        assertAll(
                { assertTrue(areEqual) }
        )
    }

    @Test
    fun `Equals to another graph, when has equal edges`() {
        // Given
        val graph = Supplier {
            val graph = DirectedMultigraph<String, Int>()
            graph.add("A" to "B", 1)
            graph.add("A" to "B", 2)
            graph.add("B" to "A", 3)
            graph.add("B" to "A", 4)
            graph.add("C" to "A", 5)
            return@Supplier graph
        }

        val graphOne = graph.get()
        val graphTwo = graph.get()

        // When
        val areEqual = graphOne == graphTwo

        // Then
        assertAll(
                { assertTrue(areEqual) }
        )
    }
}
