package pl.dopierala.bio.sbh.model.dna

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class DnaTest {

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 2, 5, 10, 100, 700, 1000])
    fun `WHEN generating DNA THEN generates a random String`(length: Int) {
        val dna = Dna.generate(length)

        assertAll(
                { assertEquals(length, dna.length, "DNA has invalid length!") },
                {
                    val distinctNucleotides = dna.chunked(1).toSet()
                    val unknownNucleotides = distinctNucleotides.subtract(Dna.nucleotides)
                    assertEquals(emptySet<String>(), unknownNucleotides, "DNA contains unknown nucleotides!")
                }
        )
    }
}
