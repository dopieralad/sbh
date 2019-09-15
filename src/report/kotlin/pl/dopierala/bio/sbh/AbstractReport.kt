package pl.dopierala.bio.sbh.solution

import org.junit.jupiter.api.BeforeEach
import pl.dopierala.bio.sbh.solution.configuration.SolutionConfiguration
import pl.dopierala.bio.sbh.solution.configuration.SolutionProperties
import pl.dopierala.bio.sbh.solution.graph.DefaultGraphFactory
import pl.dopierala.bio.sbh.solution.model.Instance
import pl.dopierala.bio.sbh.verification.maximumScore
import pl.dopierala.bio.sbh.verification.scoreAgainst
import java.io.File
import kotlin.math.max
import kotlin.system.measureTimeMillis

internal abstract class AbstractReport(reportName: String) {

    private val reportFile = File("$reportName.csv")
    private val graphFactory = DefaultGraphFactory()
    private val solutionProperties = SolutionProperties()
    private val solutionConfiguration = SolutionConfiguration()
    private val dispatcher = solutionConfiguration.dispatcher()
    private val solver = DefaultSolver(graphFactory, solutionProperties, dispatcher)

    @BeforeEach
    fun setup() {
        if (reportFile.exists().not()) {
            reportFile.writeText("DNA length;Oligonucleotide length;Generations;Ants;Evaporation;Time;Score;Score percentage\n")
        }
    }

    fun report(instance: Instance, generations: Int, ants: Int, evaporation: Double) {
        // Given
        solutionProperties.apply {
            this.generations = generations
            this.ants = ants
            this.pheromone.evaporation = evaporation
        }

        // When
        var dna = ""
        val time = measureTimeMillis {
            dna = solver.solve(instance)
        }

        // Then
        val score = max(dna scoreAgainst instance.dna, 0)
        val maximumScore = instance.dna.maximumScore()
        val scorePercentage = score.toDouble() / maximumScore * 100.0

        val (dnaLength, oligonucleotideLength) = instance
        reportFile.appendText("$dnaLength;$oligonucleotideLength;$generations;$ants;$evaporation;$time;$score;$scorePercentage\n")
    }
}
