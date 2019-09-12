package pl.dopierala.bio.sbh.solution.graph

interface GraphFactory {

    fun create(spectrum: Set<String>): DirectedMultigraph<String, Int>
}
