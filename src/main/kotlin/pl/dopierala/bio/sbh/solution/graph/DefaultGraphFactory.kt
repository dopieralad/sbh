package pl.dopierala.bio.sbh.solution.graph

import org.springframework.stereotype.Component

@Component
class DefaultGraphFactory : GraphFactory {

    override fun create(spectrum: Set<String>): Graph {
        val graph = Graph()

        spectrum.forEach { source ->
            val others = spectrum - source
            others.forEach { target ->
                val values = compute(source, target)
                values.forEach { graph.add(source to target, it) }
            }
        }

        return graph
    }

    private fun compute(source: String, target: String): Set<Int> {
        return (1 until source.length)
                .map { source.takeLast(it) }
                .filter { target.startsWith(it) }
                .map { it.length }
                .toSet()
    }
}
