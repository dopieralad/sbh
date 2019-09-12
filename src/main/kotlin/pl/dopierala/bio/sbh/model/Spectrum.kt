package pl.dopierala.bio.sbh.model

fun String.spectrum(length: Int): Set<String> = when {
    this.isEmpty() -> throw IllegalStateException("Cannot generate spectrum from empty sequence!")
    length <= 0 -> throw IllegalArgumentException("Length has to be positive!")
    length > this.length -> throw IllegalArgumentException("Length cannot exceed sequence length!")
    else -> this.windowed(length).toSet()
}
