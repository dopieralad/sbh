package pl.dopierala.bio.sbh.solution.graph

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import pl.dopierala.bio.sbh.model.generateDna
import pl.dopierala.bio.sbh.model.spectrum
import java.time.Duration
import kotlin.math.exp

internal class GraphFactoryTest {

    private val graphFactory = GraphFactory()

    @Test
    fun `Creates a graph for an empty spectrum`() {
        // Given
        val spectrum = emptySet<String>()

        // Then
        `With given spectrum, creates desired graph`(spectrum) { /* EMPTY */ }
    }

    @Test
    fun `Creates a graph for a single-letter spectrum`() {
        // Given
        val spectrum = "ABCDEFGHIJKLMNOPQRSTUWXYZ".spectrum(1)

        // Then
        `With given spectrum, creates desired graph`(spectrum) { /* EMPTY */ }
    }

    @Test
    fun `Creates a graph for a two-letter spectrum`() {
        // Given
        val spectrum = "ABBA".spectrum(2)

        // Then
        `With given spectrum, creates desired graph`(spectrum) {
            add("AB" to "BA", 1)
            add("BA" to "AB", 1)
            add("BB" to "BA", 1)
            add("AB" to "BB", 1)
        }
    }

    @Test
    fun `Creates a graph for a three-letter spectrum`() {
        // Given
        val spectrum = "ABBA".spectrum(3)

        // Then
        `With given spectrum, creates desired graph`(spectrum) {
            add("ABB" to "BBA", 1)
            add("ABB" to "BBA", 2)
            add("BBA" to "ABB", 1)
        }
    }

    @Test
    fun `Creates a graph for a four-letter spectrum`() {
        // Given
        val spectrum = "ABBAABC".spectrum(4)

        // Then
        `With given spectrum, creates desired graph`(spectrum) {
            add("ABBA" to "AABC", 1)
            add("ABBA" to "BBAA", 3)
            add("ABBA" to "BAAB", 2)
            add("BBAA" to "ABBA", 1)
            add("BBAA" to "BAAB", 3)
            add("BBAA" to "AABC", 1)
            add("BBAA" to "AABC", 2)
            add("BAAB" to "ABBA", 2)
            add("BAAB" to "BBAA", 1)
            add("BAAB" to "AABC", 3)
        }
    }

    @Test
    fun `Does not create edges between equal oligonucleotides`() {
        // Given
        val spectrum = "AAAAAAAAAAAAAAAA".spectrum(5)

        // Then
        `With given spectrum, creates desired graph`(spectrum) { /* EMPTY */ }
    }

    private fun `With given spectrum, creates desired graph`(spectrum: Set<String>, expectations: DirectedMultigraph<String, Int>.() -> Unit) {
        // When
        val resultGraph = graphFactory.create(spectrum)

        // Then
        val expectedGraph = DirectedMultigraph<String, Int>().apply(expectations)

        assertAll(
                { assertNotNull(resultGraph) },
                { assertEquals(expectedGraph, resultGraph) }
        )
    }
}
