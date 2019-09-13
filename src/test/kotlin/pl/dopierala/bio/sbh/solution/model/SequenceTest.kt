package pl.dopierala.bio.sbh.solution.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class SequenceTest {

    @Test
    fun `Creates an empty sequence`() {
        // When
        val sequence = Sequence()

        // Then
        assertAll(
                { assertNotNull(sequence) },
                { assertEquals("", sequence.get()) },
                { assertEquals(0, sequence.value()) },
                { assertEquals(0, sequence.length()) }
        )
    }

    @Test
    fun `Adds oligonucleotide to the path`() {
        // Given
        val sequence = Sequence()

        // When
        sequence.add("AB")
        sequence.add("BA", 1)

        // Then
        assertAll(
                { assertNotNull(sequence) },
                { assertEquals("ABA", sequence.get()) },
                { assertEquals(1, sequence.value()) },
                { assertEquals(3, sequence.length()) }
        )
    }

    @Test
    fun `Adds multiple oligonucleotides to the sequence`() {
        // Given
        val sequence = Sequence()

        // When
        sequence.apply {
            add("ABBB")
            add("BBBA", 3)
            add("BBAA", 3)
            add("BCAA", 1)
            add("AACD", 2)
        }

        // Then
        assertAll(
                { assertNotNull(sequence) },
                { assertEquals("ABBBAACAACD", sequence.get()) },
                { assertEquals(9, sequence.value()) },
                { assertEquals(11, sequence.length()) }
        )
    }
}
