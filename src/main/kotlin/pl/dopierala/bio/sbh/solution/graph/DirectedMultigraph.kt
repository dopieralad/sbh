package pl.dopierala.bio.sbh.solution.graph

class DirectedMultigraph<N, V> : HashMap<N, HashMap<N, HashSet<V>>>() {

    fun add(edge: Pair<N, N>, value: V) {
        getOrPut(edge.first, { HashMap() })
                .getOrPut(edge.second, { HashSet() })
                .add(value)
    }

    fun get(edge: Pair<N, N>): Set<V> {
        return getOrDefault(edge.first, HashMap())
                .getOrDefault(edge.second, HashSet())
    }
}
