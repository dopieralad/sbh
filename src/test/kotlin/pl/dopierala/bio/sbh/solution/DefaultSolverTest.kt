package pl.dopierala.bio.sbh.solution

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import pl.dopierala.bio.sbh.solution.configuration.SolutionProperties
import pl.dopierala.bio.sbh.solution.graph.DefaultGraphFactory
import pl.dopierala.bio.sbh.solution.model.Instance
import pl.dopierala.bio.sbh.verification.maximumScore
import pl.dopierala.bio.sbh.verification.scoreAgainst

internal class DefaultSolverTest {

    private val solutionProperties = SolutionProperties()
    private val graphFactory = DefaultGraphFactory()
    private val solver = DefaultSolver(graphFactory, solutionProperties)

    @Test
    fun `Solves DNA sequencing problem`() {
        // Given
        val dnaLength = 100
        val oligonucleotideLength = 12
        val instance = Instance(dnaLength, oligonucleotideLength)

        // When
        val dna = solver.solve(instance)

        // Then
        val score = dna scoreAgainst instance.dna
        val maximumScore = dna.maximumScore()
        val scorePercentage = score.toDouble() / maximumScore * 100.0

        assertAll(
                { assertNotNull(dna) },
                { assertEquals(dnaLength, dna.length) },
                { assert(scorePercentage >= 25) }
        )
    }
}
