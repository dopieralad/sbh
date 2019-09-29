package pl.dopierala.bio.sbh.shell

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.shell.standard.ShellCommandGroup
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import pl.dopierala.bio.sbh.solution.Solver
import pl.dopierala.bio.sbh.solution.model.Instance
import pl.dopierala.bio.sbh.verification.maximumScore
import pl.dopierala.bio.sbh.verification.scoreAgainst
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@ShellComponent
@ShellCommandGroup("SBH commands")
class SbhCommands(private val solver: Solver) {

    private val logger: Logger = LoggerFactory.getLogger(SbhCommands::class.java)

    @ShellMethod(value = "Solves the SBH problem.")
    fun solve(@ShellOption(value = ["-d", "--dna-length"], defaultValue = "100", help = "Length of the DNA sequence.")
              @Min(100)
              @Max(700)
              dnaLength: Int,

              @ShellOption(value = ["-o", "--oligonucleotide-length"], defaultValue = "10", help = "Length of a single oligonucleotide.")
              @Min(8)
              @Max(12)
              oligonucleotideLength: Int) {
        val instance = Instance(dnaLength, oligonucleotideLength)
        logger.info("DNA length: {}", dnaLength)
        logger.info("Oligonucleotide length: {}", oligonucleotideLength)
        logger.info("Original sequence: {}", instance.dna)

        val sequence = solver.solve(instance)
        logger.info("Computed sequence: {}", sequence)

        val score = sequence.scoreAgainst(instance.dna)
        val maximalScore = instance.dna.maximumScore()
        logger.info("Achieved score: {}%", 100.0 * score / maximalScore)
    }
}
