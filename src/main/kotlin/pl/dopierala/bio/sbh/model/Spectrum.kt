package pl.dopierala.bio.sbh.model

fun String.spectrum(size: Int): Set<String> = when {
    this.isEmpty() -> throw IllegalStateException("Cannot generate spectrum from empty sequence!")
    size <= 0 -> throw IllegalArgumentException("Size has to be positive!")
    size > this.length -> throw IllegalArgumentException("Size cannot exceed sequence length!")
    else -> this.windowed(size).toSet()
}
