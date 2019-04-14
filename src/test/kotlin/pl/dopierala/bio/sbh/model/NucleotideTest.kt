package pl.dopierala.bio.sbh.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test

internal class NucleotideTest {

    @Test
    fun `There are four Nucleotides`() {
        assertEquals(setOf("A", "C", "G", "T"), nucleotides)
    }

    @RepeatedTest(100)
    fun `Produces random Nucleotide`() {
        assertTrue(nucleotides.contains(randomNucleotide()))
    }
}