package pl.dopierala.bio.sbh.solution.model

import pl.dopierala.bio.sbh.model.generateDna
import pl.dopierala.bio.sbh.model.spectrum

data class Instance(val dnaLength: Int, val oligonucleotideLength: Int) {

    val dna = generateDna(dnaLength)
    val spectrum = dna.spectrum(oligonucleotideLength)

    fun firstOligonucleotide() = dna.take(oligonucleotideLength)
}
