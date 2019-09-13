package pl.dopierala.bio.sbh.solution.model

class Sequence {

    private var sequence = mutableListOf<Pair<String, Int>>()
    private var length = 0
    private var value = 0

    fun add(oligonucleotide: String, overlap: Int = 0) {
        sequence.add(oligonucleotide to overlap)
        length += oligonucleotide.length - overlap
        value += overlap
    }

    fun sequence(): List<Pair<String, Int>> = sequence

    fun get(): String = sequence.fold("", { dna, (oligonucleotide, overlap) -> dna + oligonucleotide.drop(overlap) })

    fun length(): Int = length

    fun value(): Int = value
    override fun toString(): String {
        return "Sequence(value=$value, length=$length, sequence=$sequence)"
    }

}
