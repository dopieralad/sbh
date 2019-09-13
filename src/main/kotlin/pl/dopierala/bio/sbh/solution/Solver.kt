package pl.dopierala.bio.sbh.solution

import pl.dopierala.bio.sbh.solution.model.Instance

interface Solver {

    fun solve(instance: Instance): String
}
