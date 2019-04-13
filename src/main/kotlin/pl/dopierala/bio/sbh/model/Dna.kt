package pl.dopierala.bio.sbh.model

object Dna {

    const val adenine = "A"
    const val cytosine = "C"
    const val guanine = "G"
    const val thymine = "T"
    val nucleotides = setOf(adenine, cytosine, guanine, thymine)

    fun generate(length: Int): String {
        return List(length) { nucleotides.random() }.joinToString("")
    }
}
