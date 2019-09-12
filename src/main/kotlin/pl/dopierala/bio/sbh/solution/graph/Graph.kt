package pl.dopierala.bio.sbh.solution.graph

class Graph : HashMap<String, HashMap<String, HashSet<Int>>>() {

    fun add(edge: Pair<String, String>, value: Int) {
        getOrPut(edge.first, { HashMap() })
                .getOrPut(edge.second, { HashSet() })
                .add(value)
    }

    fun get(edge: Pair<String, String>): Set<Int> {
        return getOrDefault(edge.first, HashMap())
                .getOrDefault(edge.second, HashSet())
    }
}
