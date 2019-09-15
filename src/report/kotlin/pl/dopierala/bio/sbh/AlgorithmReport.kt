package pl.dopierala.bio.sbh

import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import pl.dopierala.bio.sbh.solution.AbstractReport
import pl.dopierala.bio.sbh.solution.model.Instance

internal class AlgorithmReport : AbstractReport("Algorithm") {

    private val log: Logger = LoggerFactory.getLogger(AlgorithmReport::class.java)

    @Test
    fun `Reports performance for each algorithm variant`() {
        // Then
        repeat(4) { iteration ->
            // Given
            val instance = Instance(200, 8)

            listOf(0, 1, 50, 100, 150, 200, 250, 300, 400, 500).forEach { generations ->
                listOf(0, 1, 12, 24, 36, 48, 60, 72, 84, 96).forEach { ants ->
                    listOf(0.0, 0.1, 0.2, 0.3, 0.4, 0.6, 0.8, 1.0).forEach { evaporation ->
                        // Then
                        log.info("Iteration: '$iteration, Generations: '$generations', Ants: '$ants', Evaporation: '$evaporation'.")
                        report(instance, generations, ants, evaporation)
                    }
                }
            }
        }
    }
}
