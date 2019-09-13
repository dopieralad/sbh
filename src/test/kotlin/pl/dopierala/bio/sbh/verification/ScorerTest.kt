package pl.dopierala.bio.sbh.verification

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class ScorerTest {

    @Test
    fun `Calculates maximum score correctly`() {
        assertAll(
                { assertEquals(0, "".maximumScore()) },
                { assertEquals(1, "A".maximumScore()) },
                { assertEquals(2, "AB".maximumScore()) },
                { assertEquals(3, "ABC".maximumScore()) },
                { assertEquals(4, "ABCD".maximumScore()) },
                { assertEquals(5, "ABCDE".maximumScore()) }
        )
    }

    @Test
    fun `Calculates score correctly`() {
        assertAll(
                { assertEquals(0, "" scoreAgainst "", "Empty strings") },
                { assertEquals(10, "CTACTAGGGG" scoreAgainst "CTACTAGGGG", "Equal strings") },
                { assertEquals(2, "ACTG" scoreAgainst "ACTT", "Strings different on one position") },
                { assertEquals(2, "ACTT" scoreAgainst "ACTG", "Function invoked with inverted arguments") },
                { assertEquals(-4, "AAAA" scoreAgainst "CCCC", "Completely different strings") },
                { assertEquals(-7, "FDOSIFMSD" scoreAgainst "TIROMDLKF", "Random strings") }
        )
    }
}
