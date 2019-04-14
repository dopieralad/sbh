package pl.dopierala.bio.sbh.model

const val adenine = "A"
const val cytosine = "C"
const val guanine = "G"
const val thymine = "T"

val nucleotides = setOf(adenine, cytosine, guanine, thymine)

fun randomNucleotide() = nucleotides.random()
