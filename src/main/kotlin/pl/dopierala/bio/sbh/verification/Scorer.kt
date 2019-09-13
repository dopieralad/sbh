package pl.dopierala.bio.sbh.verification

const val matchScore = 1
const val mismatchScore = -1
const val gapScore = -1

fun String.maximumScore(): Int = length * matchScore

infix fun String.scoreAgainst(other: String): Int {
    val matrix = Array(this.length + 1) { IntArray(other.length + 1) }

    for (columnIndex in 0..other.length) {
        matrix[0][columnIndex] = gapScore * columnIndex
    }

    for (rowIndex in 0..this.length) {
        matrix[rowIndex][0] = gapScore * rowIndex
    }

    for (rowIndex in 1..this.length) {
        for (columnIndex in 1..other.length) {
            val matchValue: Int = if (this[rowIndex - 1] == other[columnIndex - 1]) {
                matchScore
            } else {
                mismatchScore
            }

            matrix[rowIndex][columnIndex] = listOf(
                    matrix[rowIndex][columnIndex - 1] + gapScore,
                    matrix[rowIndex - 1][columnIndex] + gapScore,
                    matrix[rowIndex - 1][columnIndex - 1] + matchValue
            ).max()!!
        }
    }

    return matrix.last().last()
}

