package pl.dopierala.bio.sbh

import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import pl.dopierala.bio.sbh.solution.AbstractReport
import pl.dopierala.bio.sbh.solution.model.Instance

internal class InstanceReport : AbstractReport("Instance") {

    private val log: Logger = LoggerFactory.getLogger(InstanceReport::class.java)

    @Test
    fun `Reports performance for different DNA lengths`() {
        // Given
        val generations = 50
        val ants = 96
        val evaporation = 0.1

        // Then
        repeat(4) { iteration ->
            listOf(50, 100, 150, 200, 250, 300, 350, 400, 500, 600, 700).forEach { dnaLength ->
                listOf(6, 8, 10, 12).forEach { oligonucleotideLength ->
                    val instance = Instance(dnaLength, oligonucleotideLength)
                    log.info("Iteration: '$iteration, DNA length: '$dnaLength', Oligonucleotide Length: '$oligonucleotideLength'.")
                    report(instance, generations, ants, evaporation)
                }
            }
        }
    }
}
