package pl.dopierala.bio.sbh.model

fun generateDna(length: Int): String = List(length) { randomNucleotide() }.joinToString("")