package pl.dopierala.bio.sbh.solution.graph

class DirectedMultigraph<N, V> {

    private val edges = mutableMapOf<N, MutableMap<N, MutableSet<V>>>()

    fun add(edge: Pair<N, N>, value: V) {
        edges.getOrPut(edge.first, { mutableMapOf() })
                .getOrPut(edge.second, { mutableSetOf() })
                .add(value)
    }

    fun get(edge: Pair<N, N>): Set<V> {
        return edges.getOrDefault(edge.first, mutableMapOf())
                .getOrDefault(edge.second, mutableSetOf())
    }
}
