package pl.dopierala.bio.sbh.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

internal class SpectrumTest {

    @Test
    fun `WHEN generating spectrum WITH valid sizes THEN generates properly windowed spectrum`() {
        assertAll(
                { assertEquals(setOf("A"), "A".spectrum(1)) },
                { assertEquals(setOf("A", "C"), "AC".spectrum(1)) },
                { assertEquals(setOf("AC"), "AC".spectrum(2)) },
                { assertEquals(setOf("AC", "CG", "GT"), "ACGT".spectrum(2)) },
                { assertEquals(setOf("ACG", "CGT"), "ACGT".spectrum(3)) },
                { assertEquals(setOf("ACGT"), "ACGT".spectrum(4)) },
                { assertEquals(setOf("AC", "CA"), "ACAC".spectrum(2)) }
        )
    }

    @Test
    fun `WHEN generating spectrum WITH invalid sizes THEN throws exception`() {
        assertAll(
                {
                    val exception = assertThrows<IllegalStateException> { "".spectrum(0) }
                    assertEquals("Cannot generate spectrum from empty sequence!", exception.message)
                },
                {
                    val exception = assertThrows<IllegalArgumentException> { "A".spectrum(-1) }
                    assertEquals("Length has to be positive!", exception.message)
                },
                {
                    val exception = assertThrows<IllegalArgumentException> { "C".spectrum(0) }
                    assertEquals("Length has to be positive!", exception.message)
                },
                {
                    val exception = assertThrows<IllegalArgumentException> { "G".spectrum(2) }
                    assertEquals("Length cannot exceed sequence length!", exception.message)
                }
        )
    }
}
