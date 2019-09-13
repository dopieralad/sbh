package pl.dopierala.bio.sbh.solution.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "sbh.solution", ignoreUnknownFields = false)
class SolutionProperties {

    var generations: Int = 100
    var ants: Int = 20
    var pheromone: Pheromone = Pheromone()

    class Pheromone {

        var evaporation: Double = 0.3
    }
}
