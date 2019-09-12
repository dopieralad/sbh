package pl.dopierala.bio.sbh.shell

import org.springframework.shell.standard.ShellCommandGroup
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@ShellComponent
@ShellCommandGroup("SBH commands")
class SbhCommands {

    @ShellMethod(value = "Solves the SBH problem.")
    fun solve(@ShellOption(value = ["-d", "--dna-length"], defaultValue = "100", help = "Length of the DNA sequence.")
              @Min(100)
              @Max(700)
              dnaLength: Int,

              @ShellOption(value = ["-o", "--oligonucleotide-length"], defaultValue = "10", help = "Length of a single oligonucleotide.")
              @Min(8)
              @Max(12)
              oligonucleotideLength: Int) {
        println("DNA length: $dnaLength, oligonucleotide length: $oligonucleotideLength.")
    }
}
