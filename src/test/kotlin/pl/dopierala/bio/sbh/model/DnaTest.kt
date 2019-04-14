package pl.dopierala.bio.sbh.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class DnaTest {

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 2, 5, 10, 100, 700, 1000])
    fun `Generates valid DNA sequences`(length: Int) {
        val dna = generateDna(length)

        assertAll(
                { assertEquals(length, dna.length) },
                {
                    val distinctNucleotides = dna.chunked(1).toSet()
                    assertTrue(nucleotides.containsAll(distinctNucleotides))
                }
        )
    }
}
